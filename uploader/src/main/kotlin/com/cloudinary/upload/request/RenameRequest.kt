package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.util.buildRenameParams
import com.cloudinary.util.toUploadResult

class RenameRequest internal constructor(
    private val fromPublicId: String,
    private val toPublicId: String,
    private val type: String?,
    private val toType: String?,
    private val overwrite: Boolean?,
    private val invalidate: Boolean?,
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration
) : AbstractUploaderRequest<UploadResult>(uploader, options, configuration) {
    override fun buildParams() = buildRenameParams(
        fromPublicId,
        toPublicId,
        type,
        toType,
        overwrite,
        invalidate
    )

    override fun execute() = uploader.callApi(this, "rename", ::toUploadResult)

    class Builder(private val fromPublicId: String, private val toPublicId: String, uploader: Uploader) :
        UploaderRequestsBuilder<RenameRequest>(uploader) {

        var type: String? = null
        var toType: String? = null
        var overwrite: Boolean = false
        var invalidate: Boolean = false

        override fun build() = RenameRequest(
            fromPublicId,
            toPublicId,
            type,
            toType,
            overwrite,
            invalidate,
            uploader,
            options,
            configuration
        )
    }
}