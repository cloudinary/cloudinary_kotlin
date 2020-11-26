package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoinWithOrReturnOriginal

class VideoSource internal constructor(
    private val publicId: String,
    private val format: Any? = null,
    override val transformation: Transformation? = null
) : BaseVideoSource {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return "video:${publicId.cldEncodePublicId().cldJoinWithOrReturnOriginal(".", format)}"
    }

    companion object {
        fun publicId(publicId: String, options: (Builder.() -> Unit)? = null): VideoSource {
            val builder = Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    @TransformationDsl
    class Builder(private val publicId: String) {
        private var format: Any? = null
        private var transformation: Transformation? = null

        fun format(format: FormatType) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build() = VideoSource(publicId, format, transformation)
    }
}