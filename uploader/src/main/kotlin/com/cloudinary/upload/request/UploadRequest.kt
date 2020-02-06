package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.http.ProgressCallback
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.util.cldIsRemoteUrl
import com.cloudinary.util.toMap
import com.cloudinary.util.toUploadResult
import java.io.File
import java.io.InputStream
import java.net.URL


class UploadRequest internal constructor(
    private val uploadLarge: Boolean,
    internal val params: UploadParams,
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration,
    payload: Payload<*>,
    progressCallback: ProgressCallback?
) : AbstractUploaderRequest<UploadResult>(uploader, options, configuration, payload, progressCallback) {
    override fun buildParams() = params.toMap()
    override fun execute() =
        if (uploadLarge) uploader.doUploadLarge(this) else uploader.callApi(this, "upload", ::toUploadResult)

    class Builder private constructor(
        private val payload: Payload<*>,
        uploader: Uploader,
        private val uploadLarge: Boolean
    ) :
        UploaderRequestsBuilder<UploadRequest>(uploader) {
        constructor(file: File, uploader: Uploader, uploadLarge: Boolean = false) : this(
            FilePayload(file),
            uploader,
            uploadLarge
        )

        constructor(
            file: String,
            uploader: Uploader,
            uploadLarge: Boolean = false
        ) : this(if (file.cldIsRemoteUrl()) UrlPayload(file) else FilePayload(File(file)), uploader, uploadLarge)

        constructor(url: URL, uploader: Uploader, uploadLarge: Boolean = false) : this(
            UrlPayload(url.toString()),
            uploader,
            uploadLarge
        )

        internal constructor(stream: InputStream, uploader: Uploader, uploadLarge: Boolean = false) : this(
            StreamPayload(stream),
            uploader,
            uploadLarge
        )

        internal constructor(byteArray: ByteArray, uploader: Uploader, uploadLarge: Boolean = false) : this(
            BytesPayload(
                byteArray
            ), uploader, uploadLarge
        )

        var params = UploadParams()
        var progressCallback: ProgressCallback? = null

        fun params(params: UploadParams.Builder.() -> Unit) {
            val builder = UploadParams.Builder()
            builder.params()
            this.params = builder.build()
        }

        override fun build() = UploadRequest(
            uploadLarge,
            params,
            uploader,
            options,
            configuration,
            payload,
            progressCallback
        )
    }
}

