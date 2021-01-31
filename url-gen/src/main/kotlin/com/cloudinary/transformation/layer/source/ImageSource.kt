package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.*
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoinWithOrReturnOriginal

class ImageSource internal constructor(
    private val publicId: String,
    private val format: Any? = null,
    override val transformation: ITransformable<Transformation>? = null
) : Source {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return publicId.cldEncodePublicId().cldJoinWithOrReturnOriginal(".", format)
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
        private var format: Any? = null
        private var transformation: ITransformable<Transformation>? = null

        fun format(format: Format) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun transformation(transformation: ITransformable<Transformation>) =
            apply { this.transformation = transformation }

        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build() = ImageSource(publicId, format, transformation)
    }
}