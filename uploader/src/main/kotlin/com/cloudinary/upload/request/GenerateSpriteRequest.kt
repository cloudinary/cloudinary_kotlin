package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.Transformation
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.GenerateSpriteResult
import com.cloudinary.util.buildGenerateSpriteParams
import com.cloudinary.util.toGenerateSpriteResult

class GenerateSpriteRequest(
    private val tag: String,
    private val transformation: Transformation?,
    private val notificationUrl: String?,
    private val async: Boolean,
    private val format: Format?,
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration
) : AbstractUploaderRequest<GenerateSpriteResult>(uploader, options, configuration) {
    override fun buildParams(): MutableMap<String, Any> {
        val transformation =
            if (format == null)
                transformation
            else
                (transformation ?: Transformation()).format(format)

        return buildGenerateSpriteParams(
            tag,
            transformation,
            notificationUrl, async
        )
    }

    override fun execute() = uploader.callApi(this, "sprite", ::toGenerateSpriteResult)

    class Builder(private val tag: String, uploader: Uploader) :
        UploaderRequestsBuilder<GenerateSpriteRequest>(uploader) {

        var transformation: Transformation? = null
        var notificationUrl: String? = null
        var async: Boolean = false
        var format: Format? = null

        fun transformation(transform: Transformation.Builder.() -> Unit) {
            val builder = Transformation.Builder()
            builder.transform()
            transformation = builder.build()
        }

        override fun build() = GenerateSpriteRequest(
            tag,
            transformation,
            notificationUrl,
            async,
            format,
            uploader,
            options,
            configuration
        )
    }
}