package com.cloudinary.android.uploader.work

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import com.cloudinary.android.uploader.request.*
import com.cloudinary.android.uploader.request.BackoffPolicy
import com.cloudinary.android.uploader.request.BackoffPolicy.EXPONENTIAL
import com.cloudinary.android.uploader.request.BackoffPolicy.LINEAR
import com.cloudinary.android.uploader.request.NetworkType
import com.cloudinary.android.uploader.request.NetworkType.*
import com.cloudinary.upload.NetworkDelegate.Companion.processResponse
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.FilePayload
import com.cloudinary.upload.request.Payload
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.toUploadResult
import java.io.InputStream
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal const val RESULT_DATA_KEY = "result"
internal const val ERROR_DATA_KEY = "error"
internal const val STATUS_CODE_DATA_KEY = "status_code"

internal const val PROGRESS_KEY = "progress"

internal const val FILE_DATA_KEY = "file"
internal const val URI_DATA_KEY = "uri"
internal const val CHUNK_SIZE_DATA_KEY = "chunk_size"
internal const val FILENAME_DATA_KEY = "filename"
internal const val API_URL_DATA_KEY = "url"
internal const val FILE_INDEX_DATA_KEY = "index"
internal const val TOTAL_LENGTH_DATA_KEY = "total_length"
internal const val MAX_RETRIES_DATA_KEY = "maxErrorRetries"
internal const val UPLOAD_ID_DATA_KEY = "unique_upload_id"
internal const val FILES_LIST_DATA_KEY = "files"
internal const val PARAM_DATA_KEY_PREFIX = "dispatcher_param:"
internal const val HEADER_DATA_KEY_PREFIX = "dispatcher_header:"

private val threads = Executors.newFixedThreadPool(10)

class UploadProgress(val bytes: Long, val totalBytes: Long)

class UploadAsyncResult(
    val workTag: String
) {
    fun getResult(context: Context): LiveData<UploaderResponse<UploadResult>> {
        return notifyWhenNotNull(
            Transformations.map(WorkManager.getInstance(context).getWorkInfosByTagLiveData(workTag)) { workInfos ->
                // if all succeeded we look for the 'done' one and construct a full response from it
                if (workInfos.all { it.state == WorkInfo.State.SUCCEEDED }) {
                    extractResult(workInfos)
                } else {
                    // look for an error or cancel:
                    workInfos.find { it.hasError() }?.toUploaderResponse()
                        ?: workInfos.find { it.state == WorkInfo.State.CANCELLED }?.toUploaderResponse()
                } // else - enqueued or processing, nothing to notify
            })
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(workTag)
    }

    fun progress(context: Context): LiveData<UploadProgress> {
        return Transformations.distinctUntilChanged(
            Transformations.map(WorkManager.getInstance(context).getWorkInfosByTagLiveData(workTag)) { workInfos ->
                val totalLength = workInfos.find {
                    it.outputData.getLong(TOTAL_LENGTH_DATA_KEY, 0) != 0L
                }?.outputData?.getLong(
                    TOTAL_LENGTH_DATA_KEY, 0
                ) ?: 0

                val bytes: Long = workInfos.sumByLong { it.progress.getLong(PROGRESS_KEY, 0) }
                UploadProgress(bytes, totalLength)
            })
    }
}

private fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

interface AsyncWorkDispatcher {
    fun uploadAsync(
        context: Context,
        request: UploadRequest
    ): UploadAsyncResult
}

internal class WorkManagerAsyncDispatcher : AsyncWorkDispatcher {
    // TODO callbacks and progress
    override fun uploadAsync(
        context: Context,
        request: UploadRequest
    ): UploadAsyncResult {
        require(request.payload != null) { "Upload request cannot have a null payload" }

        val tag = UUID.randomUUID().toString()

        // offloading from main thread to get length (this can be a long I/O operation):
        threads.submit {
            val length = ensureLength(request.payload)

            // first worker: split into chunk-size files
            // second worker and onward: upload workers per chunk, sequential.
            val chunkSize = request.options.chunkSize.toLong()

            // TODO - serialize pre-process chain to inputData
            val splitRequest = request.toSplitRequest(tag, chunkSize, length)

            val workersCount = (length / chunkSize + (if (length % chunkSize == 0L) 0 else 1)).toInt()

            var chain = WorkManager.getInstance(context).beginWith(splitRequest)

            for (i: Int in 0 until workersCount) {
                chain = chain.then(request.toUploadWorkRequest(tag, i, length))
            }

            chain.enqueue()
        }

        return UploadAsyncResult(tag)
    }

    private fun ensureLength(payload: Payload<*>) =
        // TODO: print warning when a stream is needed to get the length
        if (payload.length > 0) payload.length else payload.asInputStream().use { it.getRealLength() }
}

private fun extractResult(infos: List<WorkInfo>): UploaderResponse<UploadResult>? {
    return if (!infos.all { it.state == WorkInfo.State.SUCCEEDED }) {
        // not all workers are finished
        null
    } else {
        if (infos.size == 2) {
            // single uploader task (the first one is the splitter) - no 'done' flag in that case
//            infos.mapNotNull { it.toUploaderResponse() }.first()
            infos.find { it.tags.contains(UploadWorker::class.java.canonicalName) }?.toUploaderResponse()
        } else {
            infos.find { it.isDone() }?.toUploaderResponse()
        }
    }
}

private fun WorkInfo.isDone() = outputData.getString(RESULT_DATA_KEY)?.let(::toUploadResult)?.done == true

private fun WorkInfo.hasError(): Boolean {
    return !outputData.getString(ERROR_DATA_KEY).isNullOrEmpty()
}

private fun <T> notifyWhenNotNull(source: LiveData<T?>): LiveData<T> {
    return MediatorLiveData<T>().also { output ->
        output.addSource(source) {
            if (it != null) {
                output.value = it
            }
        }
    }
}

private fun WorkInfo.toUploaderResponse(): UploaderResponse<UploadResult>? {
    val rawResult = outputData.getString(RESULT_DATA_KEY)
    val error = outputData.getString(ERROR_DATA_KEY)
    val httpStatusCode = outputData.getInt(STATUS_CODE_DATA_KEY, -1)
    return if (rawResult.isNullOrEmpty() && error.isNullOrEmpty()) null
    else processResponse(httpStatusCode, rawResult, error, ::toUploadResult)
}

/**
 * This actually READ THE STREAM ENTIRELY to determine length. Use only if absoultely necessary and there's no
 * alternative.
 */
private fun InputStream.getRealLength(): Long {
    val buffer = ByteArray(1 * 1024 * 1024)
    var length: Long = 0
    var readBytes = 0
    do {
        length += readBytes
        readBytes = read(buffer)
    } while (readBytes > 0)

    return length
}

private fun UploadRequest.toSplitRequest(
    tag: String,
    chunkSize: Long,
    totalLength: Long
): OneTimeWorkRequest {
    val dataBuilder = Data.Builder()
        .putLong(CHUNK_SIZE_DATA_KEY, chunkSize)
        .putLong(TOTAL_LENGTH_DATA_KEY, totalLength)

    when (payload) {
        is FilePayload -> dataBuilder.putString(FILE_DATA_KEY, payload.value.absolutePath)
        is LocalUriPayload -> dataBuilder.putString(URI_DATA_KEY, payload.uri.toString())
        else -> throw Error(IllegalArgumentException("Background upload payload must be either a File or a Uri"))
    }

    return OneTimeWorkRequestBuilder<FileSplitWorker>()
        .setConstraints(adaptConstraints(this.asyncUploadConfig, true))
        .setInputData(dataBuilder.build())
        .addTag(tag)
        .build()
}

internal fun UploadRequest.toUploadWorkRequest(tag: String, index: Int, totalLength: Long): OneTimeWorkRequest {
    val builder = OneTimeWorkRequestBuilder<UploadWorker>()
        .setConstraints(adaptConstraints(this.asyncUploadConfig))
        .setInitialDelay(asyncUploadConfig.initialDelay, TimeUnit.MILLISECONDS)
        .setInputData(toWorkManagerData(index, totalLength))
        .addTag(tag)
        .setBackoffCriteria(
            adaptBackoffCriteria(asyncUploadConfig.backoffPolicy),
            asyncUploadConfig.backoffMillis,
            TimeUnit.MILLISECONDS
        )

    return builder.build()
}

private fun adaptConstraints(
    configAsync: AsyncUploadConfig,
    overrideNetworkRequired: Boolean = false
): Constraints {
    return Constraints.Builder().apply {
        if (overrideNetworkRequired) {
            setRequiredNetworkType(androidx.work.NetworkType.NOT_REQUIRED)
        } else {
            setRequiredNetworkType(adaptNetworkType(configAsync.requiredNetworkType))
        }
        setRequiresCharging(configAsync.requiresCharging)
        setRequiresDeviceIdle(configAsync.requiresIdle)
        setRequiresBatteryNotLow(configAsync.requiresBatteryNotLow)
    }.build()
}

private fun adaptNetworkType(type: NetworkType): androidx.work.NetworkType {
    return when (type) {
        CONNECTED -> androidx.work.NetworkType.CONNECTED
        UNMETERED -> androidx.work.NetworkType.UNMETERED
        NOT_ROAMING -> androidx.work.NetworkType.NOT_ROAMING
        METERED -> androidx.work.NetworkType.METERED
    }
}

private fun adaptBackoffCriteria(backoffPolicy: BackoffPolicy): androidx.work.BackoffPolicy {
    return when (backoffPolicy) {
        LINEAR -> androidx.work.BackoffPolicy.LINEAR
        EXPONENTIAL -> androidx.work.BackoffPolicy.EXPONENTIAL
    }
}

private fun UploadRequest.toWorkManagerData(index: Int, totalLength: Long): Data {
    val networkRequest = Uploader.prepareNetworkRequest("upload", this, ::toUploadResult)

    val prefixedParams = networkRequest.params.mapKeys { "$PARAM_DATA_KEY_PREFIX${it.key}" }
    val prefixedHeaders = networkRequest.headers.mapKeys { "$HEADER_DATA_KEY_PREFIX${it.key}" }

    return with(Data.Builder()) {
        putAll(prefixedParams)
        putAll(prefixedHeaders as Map<String, Any>)
        putString(FILENAME_DATA_KEY, networkRequest.filename)
        putString(API_URL_DATA_KEY, networkRequest.url)
        putInt(FILE_INDEX_DATA_KEY, index)
        putInt(CHUNK_SIZE_DATA_KEY, options.chunkSize)
        putLong(TOTAL_LENGTH_DATA_KEY, totalLength)
        putInt(MAX_RETRIES_DATA_KEY, asyncUploadConfig.maxErrorRetries)
    }.build()
}

