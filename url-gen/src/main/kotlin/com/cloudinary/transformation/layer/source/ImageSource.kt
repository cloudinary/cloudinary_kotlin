package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoinWithOrReturnOriginal

class ImageSource internal constructor(
    private val publicId: String,
    private val format: Any? = null
) : LayerSource {
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

        fun format(format: FormatType) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun build() = ImageSource(publicId, format)
    }
}