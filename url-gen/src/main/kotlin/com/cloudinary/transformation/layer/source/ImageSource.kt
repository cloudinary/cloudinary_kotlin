package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.ITransformableImage
import com.cloudinary.transformation.ImageTransformation
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldEncodePublicId

class ImageSource internal constructor(
    private val publicId: String,
    override val transformation: ITransformableImage<*>? = null
) : Source {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return publicId.cldEncodePublicId()
    }

    companion object {
        fun publicId(publicId: String, options: (Builder.() -> Unit)? = null): ImageSource {
            val builder = Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    @TransformationDsl
    class Builder(private val publicId: String) {
        private var transformation: ITransformableImage<*>? = null

        fun transformation(transformation: ITransformableImage<*>) =
            apply { this.transformation = transformation }

        fun transformation(transformation: ImageTransformation.Builder.() -> Unit) = apply {
            val builder = ImageTransformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build() = ImageSource(publicId, transformation)
    }
}