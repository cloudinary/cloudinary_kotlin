package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.expression.Expression

class SubtitlesSource private constructor(
    private val publicId: String,
    private val style: Any,
    private val backgroundColor: Color? = null,
    private val textColor: Color? = null,
    override val transformation: Transformation? = null
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
        return "subtitles:$style:$publicId"
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

        fun style(style: TextStyle) = apply { this.style = style }
        fun style(style: String) = apply { this.style = style }
        fun style(style: Expression) = apply { this.style = style }

        fun style(fontFamily: String, fontSize: Int, options: (TextStyle.Builder.() -> Unit)? = null) =
            style(fontFamily as Any, fontSize as Any, options)

        fun style(fontFamily: Any, fontSize: Any, options: (TextStyle.Builder.() -> Unit)? = null) = apply {
            val builder = TextStyle.Builder(fontFamily, fontSize)
            options?.let { builder.it() }
            style(builder.build())
        }

        fun backgroundColor(background: Color) = apply { this.backgroundColor = background }
        fun textColor(textColor: Color) = apply { this.textColor = textColor }
        private var transformation: Transformation? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun build(): SubtitlesSource {
            val safeStyle = style
            require(safeStyle != null) { "A style must be provided (font + font size are mandatory)." }
            return SubtitlesSource(publicId, safeStyle, backgroundColor, textColor, transformation)
        }
    }
}
