package com.cloudinary.upload

import com.cloudinary.Cloudinary
import com.cloudinary.http.*
import com.cloudinary.upload.request.*
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.*
import com.cloudinary.util.*
import java.io.File
import java.io.InputStream
import java.net.URL
import java.util.*

private const val DEFAULT_PREFIX = "https://api.cloudinary.com"
private const val API_VERSION = "v1_1"

private typealias StringToResult<T> = (String) -> T?

class Uploader internal constructor(
    internal val cloudinary: Cloudinary,
    private val clientFactory: HttpClientFactory = cloudinary.newHttpClientFactory()
) {
    private val parsableStatusCodes = intArrayOf(200, 400, 401, 403, 404, 420, 500)

    fun upload(file: File, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this), request)

    fun upload(file: URL, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this), request)

    fun upload(file: String, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this), request)

    fun upload(file: InputStream, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this), request)

    fun upload(file: ByteArray, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this), request)

    fun uploadLarge(file: File, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this, true), request)

    fun uploadLarge(file: URL, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this, true), request)

    fun uploadLarge(file: String, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this, true), request)

    fun uploadLarge(file: InputStream, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this, true), request)

    fun uploadLarge(file: ByteArray, request: (UploadRequest.Builder.() -> Unit)? = null) =
        buildAndExecute(UploadRequest.Builder(file, this, true), request)

    fun destroy(
        publicId: String,
        request: (DestroyRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<StatusResult> {
        val builder = DestroyRequest.Builder(publicId, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun rename(
        fromPublicId: String,
        toPublicId: String,
        request: (RenameRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<UploadResult> {
        val builder = RenameRequest.Builder(fromPublicId, toPublicId, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun explicit(
        publicId: String,
        request: (ExplicitRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<UploadResult> {
        val builder = ExplicitRequest.Builder(publicId, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun deleteByToken(
        token: String,
        request: (DeleteByTokenRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<StatusResult> {
        val builder = DeleteByTokenRequest.Builder(token, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun generateSprite(
        tag: String,
        request: (GenerateSpriteRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<GenerateSpriteResult> {
        val builder = GenerateSpriteRequest.Builder(tag, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun multi(tag: String, request: (MultiRequest.Builder.() -> Unit)? = null): UploaderResponse<MultiResult> {
        val builder = MultiRequest.Builder(tag, this)
        request?.let { builder.request() }
        return builder.build().execute()
    }

    fun createArchive(request: (ArchiveRequest.Builder.() -> Unit)): UploaderResponse<ArchiveResult> {
        val builder = ArchiveRequest.Builder(this)
        builder.request()
        return builder.build().execute()
    }

    fun addTag(
        tag: String,
        publicIds: List<String>,
        exclusive: Boolean = false,
        request: (TagsRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<TagsResult> {
        val command = if (exclusive) TagsCommand.SetExclusive else TagsCommand.Add
        val builder = TagsRequest.Builder(command, publicIds, this)
        builder.tag = tag
        request?.let { builder.it() }
        return builder.build().execute()
    }

    fun removeTag(
        tag: String,
        publicIds: List<String>,
        request: (TagsRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<TagsResult> {
        val builder = TagsRequest.Builder(TagsCommand.Remove, publicIds, this)
        builder.tag = tag
        request?.let { builder.it() }
        return builder.build().execute()
    }

    fun removeAllTags(
        publicIds: List<String>,
        request: (TagsRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<TagsResult> {
        val builder = TagsRequest.Builder(TagsCommand.RemoveAll, publicIds, this)
        request?.let { builder.it() }
        return builder.build().execute()
    }

    fun replaceTag(
        tag: String,
        publicIds: List<String>,
        request: (TagsRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<TagsResult> {
        val builder = TagsRequest.Builder(TagsCommand.Replace, publicIds, this)
        builder.tag = tag
        request?.let { builder.it() }
        return builder.build().execute()
    }

    fun addContext(
        publicIds: List<String>,
        request: (ContextRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<ContextResult> {
        val command = ContextCommand.Add
        val builder = ContextRequest.Builder(command, publicIds, this)
        request?.let { builder.it() }
        return builder.build().execute()
    }

    fun removeAllContext(
        publicIds: List<String>,
        request: (ContextRequest.Builder.() -> Unit)? = null
    ): UploaderResponse<ContextResult> {
        val builder = ContextRequest.Builder(ContextCommand.RemoveAll, publicIds, this)
        request?.let { builder.it() }
        return builder.build().execute()
    }

    // TODO pending requirements
    // fun text()

    private fun buildAndExecute(
        builder: UploadRequest.Builder,
        request: (UploadRequest.Builder.() -> Unit)?
    ): UploaderResponse<UploadResult> {
        request?.let { builder.request() }
        return builder.build().execute()
    }

    internal fun <T> callApi(
        request: AbstractUploaderRequest<*>,
        action: String,
        adapter: StringToResult<T>
    ): UploaderResponse<T> {
        val (url, params) = prepare(action, request)

        // TODO error handling
        val post = clientFactory.getClient().post(
            URL(url), request.options.headers,
            MultipartEntity().also { entity ->
                request.payload?.let { entity.addPayloadPart(it, request.options.filename) }
                params.forEach { param ->
                    if (param.value is Collection<*>) {
                        (param.value as Collection<*>).forEach {
                            entity.addTextPart("${param.key}[]", it.toString())
                        }
                    } else {
                        entity.addTextPart(param.key, param.value.toString())
                    }
                }
            },
            request.progressCallback
        )
            ?: throw Exception()

        // TODO error handling
        return processResponse(post, adapter)
    }

    internal fun doUploadLarge(
        request: UploadRequest,
        uniqueUploadId: String = randomPublicId()
    ): UploaderResponse<UploadResult> {
        // payload can't be null in upload request:
        val payload = request.payload!!
        val value = payload.value
        if (value is String && value.cldIsRemoteUrl()) return callApi(request, "upload", ::toUploadResult)

        val input: InputStream = payload.asInputStream()

        return uploadLargeParts(
            input,
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

                val builder = UploaderOptions.Builder().apply {
                    headers = extraHeaders
                    fileName?.let { this.filename = it }
                }

                val partRequest = UploadRequest.Builder(buffer, this).apply {
                    params = uploadParams
                    resourceType?.let { builder.resourceType = it }
                    options = builder.build()
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

    private fun prepare(action: String, request: AbstractUploaderRequest<*>): PreparedRequest {
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

        return PreparedRequest(url, paramsMap)
    }

    private fun <T> processResponse(httpResponse: HttpResponse, adapter: StringToResult<T>): UploaderResponse<T> {
        val result: UploaderResponse<T>
        if (parsableStatusCodes.contains(httpResponse.httpStatusCode)) {
            result = if (httpResponse.httpStatusCode == 200) {
                // parse result
                // TODO error handling
                httpResponse.content?.let { UploaderResponse(adapter(it), null) } ?: throw Exception()
            } else {
                // parse error
                // TODO error handling
                val error: UploadError = httpResponse.uploadError()
                    ?: httpResponse.headers["X-Cld-Error"]?.let {
                        UploadError(
                            Error(it)
                        )
                    }
                    ?: throw Exception()

                UploaderResponse(null, error)
            }
        } else {
            // TODO error handling
            throw Exception()
        }

        return result
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

    private data class PreparedRequest(val url: String, val params: MutableMap<String, Any>)
}
