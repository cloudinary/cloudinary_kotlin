package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.http.Payload
import com.cloudinary.http.ProgressCallback
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.util.cldIsRemoteUrl
import com.cloudinary.util.toMap
import java.io.File
import java.io.InputStream
import java.net.URL


class UploadRequest internal constructor(
    internal val params: UploadParams,
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration,
    payload: Payload<*>,
    progressCallback: ProgressCallback?
) : AbstractUploaderRequest<UploadResult>(uploader, options, configuration, payload, progressCallback) {
    override fun buildParams() = params.toMap()
    override fun execute() = uploader.doUpload(this)

    class Builder(
        private val payload: Payload<*>, uploader: Uploader
    ) :
        UploaderRequestsBuilder<UploadRequest>(uploader) {
        constructor(file: File, uploader: Uploader) : this(
            FilePayload(file),
            uploader
        )

        constructor(
            file: String,
            uploader: Uploader
        ) : this(if (file.cldIsRemoteUrl()) UrlPayload(file) else FilePayload(File(file)), uploader)

        constructor(url: URL, uploader: Uploader) : this(
            UrlPayload(url.toString()),
            uploader
        )

        internal constructor(stream: InputStream, uploader: Uploader) : this(
            StreamPayload(stream),
            uploader
        )

        internal constructor(byteArray: ByteArray, uploader: Uploader) : this(
            BytesPayload(
                byteArray
            ), uploader
        )

        var params = UploadParams()
        var progressCallback: ProgressCallback? = null

        fun params(params: UploadParams.Builder.() -> Unit) {
            val builder = UploadParams.Builder()
            builder.params()
            this.params = builder.build()
        }

        override fun build() = UploadRequest(
            params,
            uploader,
            options,
            configuration,
            payload,
            progressCallback
        )
    }
}

