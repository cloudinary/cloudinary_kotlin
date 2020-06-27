package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.layer.BaseTextSource.Builder
import com.cloudinary.util.cldSmartUrlEncode
import java.util.regex.Pattern

@TransformationDsl
internal interface ITextLayerBuilder {
    var style: TextStyle?
    var background: Color?
    var textColor: Color?
    fun fontFamily(fontFamily: String): Builder
    fun fontSize(size: Int): Builder
    fun fontSize(size: Any): Builder
    fun style(style: TextStyle): Builder
    fun style(style: TextStyle.Builder.() -> Unit): Builder
    fun background(background: Color): Builder
    fun color(textColor: Color): Builder
    fun build(): BaseTextSource
}

open class BaseTextSource internal constructor(
    type: String,
    value: String,
    fontFamily: String? = null,
    fontSize: Any? = null,
    style: TextStyle? = null,
    background: Color? = null,
    textColor: Color? = null
) :
    Source(
        listOfNotNull(
            type,
            ParamValue(
                listOfNotNull(
                    fontFamily,
                    fontSize,
                    style
                ).cldAsParamValueContent(), "_"
            ),
            value
        ),
        listOfNotNull(
            background?.cldAsBackground(),
            textColor?.cldAsColor()
        )
    ) {

    class Builder(
        private val type: String,
        private val value: String
    ) : ITextLayerBuilder {

        private var fontFamily: String? = null
        private var fontSize: Any? = null
        override var style: TextStyle? = null
        override var background: Color? = null
        override var textColor: Color? = null

        override fun fontFamily(fontFamily: String) = apply {
            this.fontFamily = fontFamily
        }

        override fun fontSize(size: Int) = apply {
            this.fontSize = size
        }

        override fun fontSize(size: Any) = apply {
            this.fontSize = size
        }


        override fun style(style: TextStyle) = apply { this.style = style }
        override fun style(style: TextStyle.Builder.() -> Unit) = apply {
            val builder = TextStyle.Builder()
            builder.style()
            style(builder.build())
        }

        override fun background(background: Color) = apply { this.background = background }
        override fun color(textColor: Color) = apply { this.textColor = textColor }

        override fun build() = BaseTextSource(type, value, fontFamily, fontSize, style, background, textColor)
    }
}

class TextSource(
    text: String,
    fontFamily: String? = null,
    fontSize: Any? = null,
    style: TextStyle? = null,
    background: Color? = null,
    textColor: Color? = null
) : BaseTextSource("text", encode(text), fontFamily, fontSize, style, background, textColor) {
    class Builder internal constructor(
        text: String,
        b: BaseTextSource.Builder = Builder("text", encode(text))
    ) : ITextLayerBuilder by b
}

class SubtitlesSource(
    publicId: String,
    style: TextStyle? = null,
    background: Color? = null,
    textColor: Color? = null
) : BaseTextSource("subtitles", publicId, null, null, style, background, textColor) {
    class Builder internal constructor(
        private val publicId: String,
        b: BaseTextSource.Builder = Builder("subtitles", publicId)
    ) : ITextLayerBuilder by b
}

class TextStyle(
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontAntialias: FontAntialias? = null,
    fontHinting: FontHinting? = null,
    textDecoration: TextDecoration? = null,
    textAlignment: TextAlignment? = null,
    stroke: Stroke? = null,
    letterSpacing: Any? = null,
    lineSpacing: Any? = null
) : ParamValue(
    listOfNotNull(
        fontWeight,
        fontStyle,
        fontAntialias?.let { NamedValue("antialias", fontAntialias, "_") },
        fontHinting?.let { NamedValue("hinting", fontHinting, "_") },
        textDecoration,
        textAlignment,
        stroke,
        letterSpacing?.let { NamedValue("letter_spacing", letterSpacing, "_") },
        lineSpacing?.let { NamedValue("line_spacing", lineSpacing, "_") }
    ).cldAsParamValueContent(), "_"
) {

    class Builder {
        private var fontWeight: FontWeight? = null
        private var fontStyle: FontStyle? = null
        private var fontAntialias: FontAntialias? = null
        private var fontHinting: FontHinting? = null
        private var textDecoration: TextDecoration? = null
        private var textAlignment: TextAlignment? = null
        private var stroke: Stroke? = null
        private var letterSpacing: Any? = null
        private var lineSpacing: Any? = null

        fun fontWeight(fontWeight: FontWeight) = apply { this.fontWeight = fontWeight }
        fun fontStyle(fontStyle: FontStyle) = apply { this.fontStyle = fontStyle }
        fun fontAntialias(fontAntialias: FontAntialias) = apply { this.fontAntialias = fontAntialias }
        fun fontHinting(fontHinting: FontHinting) = apply { this.fontHinting = fontHinting }
        fun textDecoration(textDecoration: TextDecoration) = apply { this.textDecoration = textDecoration }
        fun textAlignment(textAlignment: TextAlignment) = apply { this.textAlignment = textAlignment }
        fun stroke(stroke: Stroke) = apply { this.stroke = stroke }
        fun letterSpacing(letterSpacing: Any) = apply { this.letterSpacing = letterSpacing }
        fun lineSpacing(lineSpacing: Any) = apply { this.lineSpacing = lineSpacing }
        fun letterSpacing(letterSpacing: Float) = apply { this.letterSpacing = letterSpacing }
        fun lineSpacing(lineSpacing: Float) = apply { this.lineSpacing = lineSpacing }

        fun build() = TextStyle(
            fontWeight,
            fontStyle,
            fontAntialias,
            fontHinting,
            textDecoration,
            textAlignment,
            stroke,
            letterSpacing,
            lineSpacing
        )
    }
}

enum class FontWeight(private val value: String) {
    NORMAL("normal"),
    BOLD("bold");

    override fun toString(): String {
        return value
    }
}

enum class FontStyle(private val value: String) {
    NORMAL("normal"),
    ITALIC("italic");

    override fun toString(): String {
        return value
    }
}

enum class TextDecoration(private val value: String) {
    NORMAL("normal"),
    UNDERLINE("underline"),
    STRIKETHROUGH("strikethrough");

    override fun toString(): String {
        return value
    }
}

enum class TextAlignment(private val value: String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right"),
    END("end"),
    START("start"),
    JUSTIFY("justify");

    override fun toString(): String {
        return value
    }
}

enum class Stroke(private val value: String) {
    NONE("none"),
    STROKE("stroke");

    override fun toString(): String {
        return value
    }
}

enum class FontAntialias(private val value: String) {
    NONE("none"),
    GRAY("gray"),
    SUBPIXEL("subpixel"),
    FAST("fast"),
    GOOD("good"),
    BEST("best");

    override fun toString(): String {
        return value
    }
}

enum class FontHinting(private val value: String) {
    NONE("none"),
    SLIGHT("slight"),
    MEDIUM("medium"),
    FULL("full");

    override fun toString(): String {
        return value
    }
}

private fun encode(text: String): String {
    val result = StringBuffer()

    // Don't encode interpolation expressions e.g. $(variable)
    val m = Pattern.compile("\\$\\([a-zA-Z]\\w+\\)").matcher(text)
    var start = 0
    while (m.find()) {
        result.append(text.substring(start, m.start()).cldSmartUrlEncode()) // append encoded pre-match
        result.append(m.group()) // append match
        start = m.end()
    }

    result.append(text.substring(start).cldSmartUrlEncode())

    return result.toString().replace("%2C", "%252C").replace("/", "%252F")
}