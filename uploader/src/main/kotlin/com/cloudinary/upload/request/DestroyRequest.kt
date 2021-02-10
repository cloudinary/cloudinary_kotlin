package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.StatusResult
import com.cloudinary.util.buildDestroyParams
import com.cloudinary.util.toGenericResult

class DestroyRequest internal constructor(
    private val publicId: String,
    private val type: String?,
    private val invalidate: Boolean,
    uploader: Uploader,
    options: UploaderOptions,
    cloudinaryConfig: CloudinaryConfig
) : AbstractUploaderRequest<StatusResult>(uploader, options, cloudinaryConfig) {

    override fun execute() = uploader.callApi(this, "destroy", ::toGenericResult)
    override fun buildParams() = buildDestroyParams(publicId, type, invalidate)

    class Builder(private val publicId: String, uploader: Uploader) :
        UploaderRequestsBuilder<DestroyRequest>(uploader) {

        var type: String? = null
        var invalidate: Boolean = false

        override fun build() =
            DestroyRequest(publicId, type, invalidate, uploader, options, cloudinaryConfig)
    }
}

