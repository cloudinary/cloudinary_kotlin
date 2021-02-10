package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.*
import com.cloudinary.util.cldJoinWithOrReturnOriginal
import com.cloudinary.util.cldToBase64

class FetchSource internal constructor(
    private val remoteUrl: String,
    private val format: Any? = null,
    override val transformation: ITransformableImage<*>? = null
) : Source {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return "fetch:${remoteUrl.cldToBase64().cldJoinWithOrReturnOriginal(".", format)}"
    }

    companion object {
        fun url(url: String, options: (Builder.() -> Unit)? = null): FetchSource {
            val builder = Builder(url)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    @TransformationDsl
    class Builder(private val remoteUrl: String) {
        private var format: Any? = null
        private var transformation: ITransformableImage<*>? = null

        fun format(format: Format) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun transformation(transformation: ITransformableImage<*>) = apply { this.transformation = transformation }
        fun transformation(transformation: ImageTransformation.Builder.() -> Unit) = apply {
            val builder = ImageTransformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build() = FetchSource(remoteUrl, format, transformation)
    }
}
