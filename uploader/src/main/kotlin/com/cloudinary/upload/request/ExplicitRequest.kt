package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.util.toMap
import com.cloudinary.util.toUploadResult

class ExplicitRequest internal constructor(
    private val publicId: String,
    private val params: UploadParams,
    uploader: Uploader,
    options: UploaderOptions,
    cloudinaryConfig: CloudinaryConfig
) : AbstractUploaderRequest<UploadResult>(uploader, options, cloudinaryConfig) {

    // public Id is sent as part of params:
    override fun buildParams() = params.copy(publicId = publicId).toMap()

    override fun execute() = uploader.callApi(this, "explicit", ::toUploadResult)

    class Builder constructor(
        private val publicId: String,
        uploader: Uploader
    ) :
        UploaderRequestsBuilder<ExplicitRequest>(uploader) {
        var params = UploadParams()

        fun params(params: UploadParams.Builder.() -> Unit) {
            val builder = UploadParams.Builder()
            builder.params()
            this.params = builder.build()
        }

        override fun build() = ExplicitRequest(publicId, params, uploader, options, cloudinaryConfig)
    }
}
