package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.StatusResult
import com.cloudinary.util.buildDeleteByTokenParams
import com.cloudinary.util.toGenericResult

class DeleteByTokenRequest internal constructor(
    private val token: String,
    uploader: Uploader,
    options: UploaderOptions,
    cloudinaryConfig: CloudinaryConfig
) : AbstractUploaderRequest<StatusResult>(uploader, options, cloudinaryConfig) {
    override fun buildParams() = buildDeleteByTokenParams(token)
    override fun execute() = uploader.callApi(this, "delete_by_token", ::toGenericResult)

    class Builder(private val token: String, uploader: Uploader) :
        UploaderRequestsBuilder<DeleteByTokenRequest>(uploader) {

        override fun build() = DeleteByTokenRequest(token, uploader, options, cloudinaryConfig)
    }
}


