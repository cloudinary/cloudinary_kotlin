package com.cloudinary.android.uploader.request

import com.cloudinary.config.Configuration
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.BaseUploadRequestBuilder
import com.cloudinary.upload.request.Payload
import com.cloudinary.upload.request.UploaderOptions
import com.cloudinary.upload.request.params.UploadParams
import java.util.*

class UploadRequest(
    params: UploadParams,
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration,
    payload: Payload<*>,
    internal val asyncUploadConfig: AsyncUploadConfig
) : com.cloudinary.upload.request.UploadRequest(
    params,
    uploader,
    options,
    configuration,
    payload,
    null
) {
    val requestId = UUID.randomUUID().toString()

    class Builder(payload: Payload<*>, uploader: Uploader) :
        BaseUploadRequestBuilder<UploadRequest>(payload, uploader) {

        private var asyncUploadConfig: AsyncUploadConfig = AsyncUploadConfig.default()

        // TODO why is this a function but in the base class those are vars?
        fun uploadPolicy(asyncUploadConfig: AsyncUploadConfig) {
            this.asyncUploadConfig = asyncUploadConfig
        }

        fun uploadPolicy(options: AsyncUploadConfig.Builder.() -> Unit) {
            val builder = AsyncUploadConfig.Builder()
            builder.options()
            uploadPolicy(builder.build())
        }

        override fun build() = UploadRequest(
            params,
            uploader,
            options,
            configuration,
            payload,
            asyncUploadConfig
        )
    }
}