package com.cloudinary.android.uploader.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.cloudinary.Cloudinary
import com.cloudinary.upload.NetworkRequest
import com.cloudinary.upload.request.FilePayload
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.uploader
import com.cloudinary.util.toUploadResult
import java.io.File
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

class UploadWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        var file: File? = null
        var result = Result.failure()

        try {
            var error: String? = null

            // gather all data
            val uploadId = inputData.getString(UPLOAD_ID_DATA_KEY)
            val absoluteBasePath = getAbsoluteFilePath(applicationContext)
            val files = inputData.getStringArray(FILES_LIST_DATA_KEY)
            val index = inputData.getInt(FILE_INDEX_DATA_KEY, -1)
            val totalLength = inputData.getLong(TOTAL_LENGTH_DATA_KEY, -1)
            val chunkSize = inputData.getInt(CHUNK_SIZE_DATA_KEY, -1)

            // validate all data
            if (chunkSize < 0) error = "Chunk size is invalid: $chunkSize"
            if (error.isNullOrEmpty() && uploadId.isNullOrEmpty()) error = "Upload id is empty: $uploadId"
            if (error.isNullOrEmpty() && totalLength < 0) error = "Total length is invalid: $totalLength"
            if (error.isNullOrEmpty() && files == null) error = "Files array is null"
            if (error.isNullOrEmpty() && files!!.isEmpty()) error = "Files array is empty"
            if (error.isNullOrEmpty() && (index < 0 || index > files!!.size - 1)) error =
                "Index $index not in range, files size is ${files!!.size}"

            result = if (error.isNullOrEmpty()) {
                progress(0)
                // get the file and build the upload request
                val fullPath = "$absoluteBasePath${File.separator}${files!![index]}"
                file = File(fullPath)
                val request = inputData.toNetworkRequest(uploadId!!, index, chunkSize, totalLength, file)

                dispatch(request, files, uploadId).also {
                    if (it is Result.Success) {
                        // TODO progress should come from within the dispatch when implemented
                        progress(file.length())
                    }
                }
            } else {
                Result.failure(
                    Data.Builder().putString(ERROR_DATA_KEY, error).build()
                )
            }

            return result
        } finally {
            if (result != Result.retry())
                file?.delete()
        }
    }

    private suspend fun progress(progress: Long) {
        setProgress(workDataOf(PROGRESS_KEY to progress))
    }

    private fun dispatch(
        request: NetworkRequest<UploadResult>,
        files: Array<String>,
        uploadId: String?
    ): Result {
        return try {
            val response = Cloudinary.get().uploader().networkDelegate.callApi(request)

            with(
                Data.Builder()
                    .putInt(STATUS_CODE_DATA_KEY, response.responseCode)
                    // propagate files array and upload id to next worker in chain:
                    .putStringArray(FILES_LIST_DATA_KEY, files)
                    .putString(UPLOAD_ID_DATA_KEY, uploadId)
            ) {
                if (response.data != null) {
                    putString(RESULT_DATA_KEY, response.rawResponse).build()
                    Result.success(build())
                } else {
                    putString(ERROR_DATA_KEY, response.error?.error?.message).build()
                    Result.failure(build())
                }
            }
        } catch (e: IOException) {
            if (shouldRetry(e)) {
                Result.retry()
            } else {
                Result.failure(Data.Builder().putString(ERROR_DATA_KEY, e.message).build())
            }
        }
    }

    /**
     * Inspect the exception as well as determine if we're over max-retries configured
     */
    private fun shouldRetry(e: IOException): Boolean {
        val maxRetries = inputData.getInt(MAX_RETRIES_DATA_KEY, 0)
        return runAttemptCount < maxRetries &&
                (e is SocketException || e is SocketTimeoutException)
    }
}

private fun Data.toNetworkRequest(
    uploadId: String,
    index: Int,
    chunkSize: Int,
    totalLength: Long,
    file: File
): NetworkRequest<UploadResult> {
    val params = this.keyValueMap.filter { it.key.startsWith(PARAM_DATA_KEY_PREFIX) }
        .mapKeys { it.key.substringAfter(PARAM_DATA_KEY_PREFIX) }.toMutableMap()

    val headers = this.keyValueMap.filter { it.key.startsWith(HEADER_DATA_KEY_PREFIX) }
        .mapKeys { it.key.substringAfter(HEADER_DATA_KEY_PREFIX) }.mapValues { it.value.toString() }.toMutableMap()

    headers["X-Unique-Upload-Id"] = uploadId
    headers["Content-Range"] = contentRange(index, chunkSize, totalLength, file)
    val url = getString(API_URL_DATA_KEY) ?: throw Error(IllegalStateException("Worker Data must contain an Api url"))
    val filename = getString(FILENAME_DATA_KEY)

    return NetworkRequest(url, filename, headers, params, ::toUploadResult, FilePayload(file), null)
}

private fun contentRange(index: Int, chunkSize: Int, totalLength: Long, file: File): String {
    val offsetStart = index * chunkSize
    val offsetEnd = offsetStart + file.length().toInt() - 1
    return "bytes $offsetStart-$offsetEnd/$totalLength"
}