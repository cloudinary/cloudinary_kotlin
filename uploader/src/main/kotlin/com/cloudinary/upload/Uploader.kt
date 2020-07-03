package com.cloudinary.upload

import com.cloudinary.Cloudinary
import com.cloudinary.http.HttpClientFactory
import com.cloudinary.http.ProgressCallback
import com.cloudinary.httpClientFactory
import com.cloudinary.upload.request.AbstractUploaderRequest
import com.cloudinary.upload.request.UploadRequest
import com.cloudinary.upload.request.UploaderOptions
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.*
import java.io.InputStream
import java.util.*

private const val DEFAULT_RESOURCE_TYPE = "image"
private const val API_VERSION = "v1_1"

class Uploader internal constructor(val cloudinary: Cloudinary, clientFactory: HttpClientFactory? = null) {
    val networkDelegate = NetworkDelegate(clientFactory ?: cloudinary.httpClientFactory)
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
        require(request.payload != null) { "An upload request must have a payload" }
        val payload = request.payload
        val value = payload.value

        val chunkSize = request.options.chunkSize ?: request.configuration.chunkSize
        // if it's a remote url or the total size is known and smaller than chunk size we fallback to
        // a regular upload api (no need for chunks)
        if ((value is String && value.cldIsRemoteUrl()) || (payload.length in 1 until chunkSize.toLong())) {
            return callApi(request, "upload", ::toUploadResult)
        }

        payload.asInputStream().use {
            return uploadLargeParts(
                it,
                request.params,
                request.options.resourceType,
                request.options.filename ?: payload.name,
                chunkSize,
                payload.length,
                uniqueUploadId,
                request.progressCallback
            )
        }
    }

    // TODO - this needs to be rewritten
    private fun uploadLargeParts(
        input: InputStream,
        uploadParams: UploadParams,
        resourceType: String?,
        fileName: String?,
        chunkSize: Int,
        length: Long,
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
        var totalBytes = 0L
        var response: UploaderResponse<UploadResult>
        val knownLengthBeforeUpload = currLength
        var totalBytesUploaded = 0L
        while (true) {
            bytesRead = input.read(buffer, currentBufferSize, chunkSize - currentBufferSize)
            var atEnd = bytesRead == -1
            val fullBuffer = !atEnd && bytesRead + currentBufferSize == chunkSize
            if (!atEnd) currentBufferSize += bytesRead
            if (atEnd || fullBuffer) {
                totalBytes += currentBufferSize.toLong()
                val currentLoc = chunkSize * partNumber
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
        return networkDelegate.callApi(prepareNetworkRequest(action, request, adapter))
    }

    companion object {
        fun <T> prepareNetworkRequest(
            action: String,
            request: AbstractUploaderRequest<*>,
            adapter: StringToResult<T>
        ): NetworkRequest<T> {
            val paramsMap = request.buildParams()

            val config = request.configuration
            val prefix = config.uploadPrefix
            val cloudName = config.cloudName
            val resourceType =
                if (action != "delete_by_token") (request.options.resourceType ?: DEFAULT_RESOURCE_TYPE) else null

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

            return NetworkRequest<T>(
                url,
                request.options.filename,
                request.options.headers,
                paramsMap,
                adapter,
                request.payload,
                request.progressCallback
            )
        }
    }
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