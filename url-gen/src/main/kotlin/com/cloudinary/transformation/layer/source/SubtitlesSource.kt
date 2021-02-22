package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.*
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.util.cldEncodePublicId

class SubtitlesSource private constructor(
    private val publicId: String,
    private val style: Any?,
    private val backgroundColor: Color? = null,
    private val textColor: Color? = null,
    override val transformation: ITransformableImage<*>? = null
) : BaseVideoSource {

    // CODE SMELL: since the container of this source may need to resort the params, we cannot fully encapsulate
    // the inner structure. The extras are excluded from the toString() method.
    override fun extraComponents(): List<Param> {
        return listOfNotNull(
            backgroundColor?.let { Param("b", it) },
            textColor?.let { Param("co", it) }
        )
    }

    // See comment above - this method does not include the extras!
    override fun toString(): String {
        return "subtitles".joinWithValues(style, publicId.cldEncodePublicId())
    }

    companion object {
        fun publicId(publicId: String, options: Builder.() -> Unit): SubtitlesSource {
            val builder = Builder(publicId)
            builder.options()
            return builder.build()
        }
    }

    class Builder(private val publicId: String) {

        private var style: Any? = null
        private var backgroundColor: Color? = null
        private var textColor: Color? = null
        private var transformation: ITransformableImage<*>? = null

        fun textStyle(style: TextStyle) = apply { this.style = style }
        fun textStyle(style: String) = apply { this.style = style }
        fun textStyle(style: Expression) = apply { this.style = style }

        fun textStyle(fontFamily: String, fontSize: Int, options: (TextStyle.Builder.() -> Unit)? = null) =
            textStyle(fontFamily as Any, fontSize as Any, options)

        fun textStyle(fontFamily: Any, fontSize: Any, options: (TextStyle.Builder.() -> Unit)? = null) = apply {
            val builder = TextStyle.Builder(fontFamily, fontSize)
            options?.let { builder.it() }
            textStyle(builder.build())
        }

        fun backgroundColor(background: Color) = apply { this.backgroundColor = background }
        fun textColor(textColor: Color) = apply { this.textColor = textColor }

        fun transformation(transformation: ITransformableImage<*>) = apply { this.transformation = transformation }
        fun transformation(transformation: ImageTransformation.Builder.() -> Unit) = apply {
            val builder = ImageTransformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build(): SubtitlesSource {
            return SubtitlesSource(publicId, style, backgroundColor, textColor, transformation)
        }
    }
}
