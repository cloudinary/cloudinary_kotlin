package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.transformation.Transformation
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.MultiResult
import com.cloudinary.util.buildMultiParams
import com.cloudinary.util.toMultiResult

class MultiRequest(
    private val tag: String,
    private val transformation: Transformation?,
    private val notificationUrl: String?,
    private val async: Boolean,
    private val format: String?,
    uploader: Uploader,
    options: UploaderOptions,
    cloudinaryConfig: CloudinaryConfig
) : AbstractUploaderRequest<MultiResult>(uploader, options, cloudinaryConfig) {
    override fun buildParams() = buildMultiParams(
        tag,
        transformation,
        format,
        notificationUrl, async
    )

    override fun execute() = uploader.callApi(this, "multi", ::toMultiResult)

    class Builder(private val tag: String, uploader: Uploader) : UploaderRequestsBuilder<MultiRequest>(uploader) {
        var transformation: Transformation? = null
        var notificationUrl: String? = null
        var async: Boolean = false
        var format: String? = null

        fun transformation(transform: Transformation.Builder.() -> Unit) {
            val builder = Transformation.Builder()
            builder.transform()
            transformation = builder.build()
        }

        override fun build() = MultiRequest(
            tag,
            transformation,
            notificationUrl,
            async,
            format,
            uploader,
            options,
            cloudinaryConfig
        )
    }
}