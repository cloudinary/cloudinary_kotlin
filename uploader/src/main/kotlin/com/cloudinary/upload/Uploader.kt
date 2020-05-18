package com.cloudinary.upload

import com.cloudinary.Cloudinary
import com.cloudinary.http.HttpClientFactory
import com.cloudinary.http.ProgressCallback
import com.cloudinary.upload.request.AbstractUploaderRequest
import com.cloudinary.upload.request.UploadRequest
import com.cloudinary.upload.request.UploaderOptions
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.*
import java.io.InputStream
import java.util.*

private const val DEFAULT_PREFIX = "https://api.cloudinary.com"
private const val API_VERSION = "v1_1"

class Uploader internal constructor(
    internal val cloudinary: Cloudinary,
    clientFactory: HttpClientFactory? = null
) {
    private val networkDelegate = clientFactory?.let {
        NetworkDelegate(cloudinary.userAgent, cloudinary.config.apiConfig, it)
    } ?: NetworkDelegate(cloudinary.userAgent, cloudinary.config.apiConfig)

    internal fun buildAndExecute(
        builder: UploadRequest.Builder,
        request: (UploadRequest.Builder.() -> Unit)?
    ): UploaderResponse<UploadResult> {
        request?.let { builder.request() }
        return builder.build().execute()
    }

    internal fun doUpload(
        request: UploadRequest,
        uniqueUploadId: String = randomPublicId()
    ): UploaderResponse<UploadResult> {
        // payload can't be null in upload request:
        val payload = request.payload!!
        val value = payload.value

        // if it's a remote url or the total size is known and smaller than the minimum chunk size we fallback to
        // a regular upload api (no need for chunks)
        if ((value is String && value.cldIsRemoteUrl()) || (payload.length in 1 until request.options.chunkSize)) {
            return callApi(request, "upload", ::toUploadResult)
        }

        payload.asInputStream().use {
            return uploadLargeParts(
                it,
                request.params,
                request.options.resourceType,
                request.options.filename ?: payload.name,
                request.options.chunkSize,
                payload.length,
                request.options.offset,
                uniqueUploadId,
                request.progressCallback
            )
        }
    }

    private fun uploadLargeParts(
        input: InputStream,
        uploadParams: UploadParams,
        resourceType: String?,
        fileName: String?,
        chunkSize: Int,
        length: Long,
        offset: Long,
        uniqueUploadId: String,
        progress: ProgressCallback? = null
    ): UploaderResponse<UploadResult> {
        var currLength = length
        val extraHeaders = mutableMapOf<String, String>()

        extraHeaders["X-Unique-Upload-Id"] = uniqueUploadId

        var buffer = ByteArray(chunkSize)
        val nibbleBuffer = ByteArray(1)
        var bytesRead: Int
        var currentBufferSize = 0
        var partNumber = 0
        var totalBytes = offset
        var response: UploaderResponse<UploadResult>
        val knownLengthBeforeUpload = currLength
        var totalBytesUploaded = offset
        input.skip(offset)
        while (true) {
            bytesRead = input.read(buffer, currentBufferSize, chunkSize - currentBufferSize)
            var atEnd = bytesRead == -1
            val fullBuffer = !atEnd && bytesRead + currentBufferSize == chunkSize
            if (!atEnd) currentBufferSize += bytesRead
            if (atEnd || fullBuffer) {
                totalBytes += currentBufferSize.toLong()
                val currentLoc = offset + chunkSize * partNumber
                if (!atEnd) { //verify not on end - try read another byte
                    bytesRead = input.read(nibbleBuffer, 0, 1)
                    atEnd = bytesRead == -1
                }
                if (atEnd) {
                    if (currLength == -1L) currLength = totalBytes
                    val finalBuffer = ByteArray(currentBufferSize)
                    System.arraycopy(buffer, 0, finalBuffer, 0, currentBufferSize)
                    buffer = finalBuffer
                }
                val range = String.format(
                    Locale.US,
                    "bytes %d-%d/%d",
                    currentLoc,
                    currentLoc + currentBufferSize - 1,
                    currLength
                )
                extraHeaders["Content-Range"] = range

                val optionsBuilder = UploaderOptions.Builder().apply {
                    headers = extraHeaders
                    fileName?.let { this.filename = it }
                }

                val partRequest = UploadRequest.Builder(buffer, this).apply {
                    params = uploadParams
                    resourceType?.let { optionsBuilder.resourceType = it }
                    options = optionsBuilder.build()
                }

                // wrap the callback with another callback to account for multiple parts
                val bytesUploadedSoFar = totalBytesUploaded
                progress?.let {
                    partRequest.progressCallback = { uploaded, _ ->
                        progress(
                            bytesUploadedSoFar + uploaded,
                            knownLengthBeforeUpload
                        )
                    }
                }

                response = partRequest.build().execute()

                if (atEnd) break
                buffer[0] = nibbleBuffer[0]
                totalBytesUploaded += currentBufferSize.toLong()
                currentBufferSize = 1
                partNumber++
            }
        }

        return response
    }

    internal fun <T> callApi(
        request: AbstractUploaderRequest<*>,
        action: String,
        adapter: StringToResult<T>
    ): UploaderResponse<T> {
        val (url, params) = prepare(action, request)
        return networkDelegate.callApi(request, url, params, adapter)
    }
}

private fun prepare(action: String, request: AbstractUploaderRequest<*>): Pair<String, MutableMap<String, Any>> {
    val paramsMap = request.buildParams()

    val config = request.configuration
    val prefix = config.uploadPrefix ?: DEFAULT_PREFIX
    val cloudName = config.cloudName
    val resourceType = if (action != "delete_by_token") (request.options.resourceType ?: "image") else null

    if (requiresSigning(action, paramsMap, request)) {
        config.apiKey?.let {
            // no signature - we need to sign using api secret if present:
            val apiSecret = request.configuration.apiSecret
                ?: throw IllegalArgumentException("Must supply api_secret")

            paramsMap["timestamp"] = (System.currentTimeMillis() / 1000L).asCloudinaryTimestamp()
            paramsMap["signature"] = apiSignRequest(paramsMap, apiSecret)

            paramsMap["api_key"] = it
        }
    }

    val url = listOfNotNull(prefix, API_VERSION, cloudName, resourceType, action).joinToString("/")

    return Pair(url, paramsMap)
}

private fun requiresSigning(
    action: String,
    params: Map<String, Any>,
    request: AbstractUploaderRequest<*>
): Boolean {
    val missingSignature = params["signature"]?.toString().isNullOrBlank()
    val signedRequest = !request.options.unsigned
    val actionRequiresSigning = action != "delete_by_token"
    return missingSignature && signedRequest && actionRequiresSigning
}