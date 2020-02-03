package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
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
    configuration: Configuration
) : AbstractUploaderRequest<StatusResult>(uploader, options, configuration) {

    override fun execute() = uploader.callApi(this, "destroy", ::toGenericResult)
    override fun buildParams() = buildDestroyParams(publicId, type, invalidate)

    class Builder(private val publicId: String, uploader: Uploader) :
        UploaderRequestsBuilder<DestroyRequest>(uploader) {

        var type: String? = null
        var invalidate: Boolean = false

        override fun build() =
            DestroyRequest(publicId, type, invalidate, uploader, options, configuration)
    }
}

