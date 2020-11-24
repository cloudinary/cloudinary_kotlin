package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.expression.Expression

class TextSource private constructor(
    private val text: Any,
    private val style: Any,
    private val background: Color? = null,
    private val textColor: Color? = null
) : LayerSource {

    // CODE SMELL: since the container of this source may need to resort the params, we cannot fully encapsulate
    // the inner structure. The extras are excluded from the toString() method.
    override fun extraComponents(): List<Param> {
        return listOfNotNull(
            background?.let { Param("b", it) },
            textColor?.let { Param("co", it) }
        )
    }

    // See comment above - this method does not include the extras!
    override fun toString(): String {
        return "text:$style:$text"
    }

    companion object {
        fun text(text: String, options: Builder.() -> Unit): TextSource {
            val builder = Builder(text)
            builder.options()
            return builder.build()
        }

        fun text(text: Expression, options: Builder.() -> Unit): TextSource {
            val builder = Builder(text)
            builder.options()
            return builder.build()
        }
    }

    class Builder(private val text: Any) {

        private var style: Any? = null
        private var background: Color? = null
        private var textColor: Color? = null

        fun style(style: TextStyle) = apply { this.style = style }
        fun style(style: String) = apply { this.style = style }
        fun style(style: Expression) = apply { this.style = style }

        fun style(options: TextStyle.Builder.() -> Unit) = apply {
            val builder = TextStyle.Builder()
            builder.options()
            style(builder.build())
        }

        fun backgroundColor(background: Color) = apply { this.background = background }
        fun textColor(textColor: Color) = apply { this.textColor = textColor }

        fun build(): TextSource {
            val safeStyle = style
            require(safeStyle != null) { "A style must be provided (font + font size are mandatory)." }
            return TextSource(text, safeStyle, background, textColor)
        }
    }
}
