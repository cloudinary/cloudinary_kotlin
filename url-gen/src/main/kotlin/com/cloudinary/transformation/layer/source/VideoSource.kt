package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldEncodePublicId

class VideoSource internal constructor(
    private val publicId: String,
    override val transformation: Transformation? = null
) : BaseVideoSource {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return "video:${publicId.cldEncodePublicId()}"
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
        private var transformation: Transformation? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build() = VideoSource(publicId, transformation)
    }
}