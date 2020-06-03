package com.cloudinary.android.uploader.work

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.cloudinary.android.uploader.request.LocalUriPayload
import com.cloudinary.upload.request.FilePayload
import com.cloudinary.upload.request.Payload
import com.cloudinary.util.randomPublicId
import com.cloudinary.util.splitToFiles
import java.io.File
import java.util.*

private const val DEFAULT_ANDROID_CHUNK_SIZE = 5 * 1024 * 1024

internal class FileSplitWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // TODO - pre-process before splitting
        val payload = inputData.getPayload(applicationContext)
        val absoluteBasePath = getAbsoluteFilePath(applicationContext)
        val relativePath = "com.cloudinary.android${File.separator}${UUID.randomUUID()}"
        val path = File("$absoluteBasePath${File.separator}$relativePath")
        if (path.mkdirs()) {

            val chunkSize = inputData.getInt(CHUNK_SIZE_DATA_KEY, DEFAULT_ANDROID_CHUNK_SIZE)

            // TODO preprocessing should come here right before splitting
            val files = payload.asInputStream().splitToFiles(chunkSize, path)

            val uniqueUploadId: String = randomPublicId()

            val filePaths = files.map { it.absolutePath.substringAfter(absoluteBasePath) }

            return Result.success(
                Data.Builder()
                    .putStringArray(FILES_LIST_DATA_KEY, filePaths.toTypedArray())
                    .putString(UPLOAD_ID_DATA_KEY, uniqueUploadId)
                    .putLong(TOTAL_LENGTH_DATA_KEY, inputData.getLong(TOTAL_LENGTH_DATA_KEY, 0))
                    .build()
            )
        } else {
            return Result.failure(
                Data.Builder().putString(ERROR_DATA_KEY, "Could not create temp folder: $path").build()
            )
        }
    }
}

internal fun getAbsoluteFilePath(context: Context) = context.filesDir.absolutePath

internal fun Data.getPayload(context: Context): Payload<*> {
    val uri = getString(URI_DATA_KEY)
    val file = getString(FILE_DATA_KEY)

    return when {
        file != null -> FilePayload(File(file))
        uri != null -> LocalUriPayload(context, Uri.parse(getString(URI_DATA_KEY)!!))
        else -> throw Error(IllegalStateException("Worker Data doesn't contain a URI nor a File"))
    }
}