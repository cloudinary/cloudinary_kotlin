package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.layer.BaseTextLayerSource.Builder
import com.cloudinary.util.cldSmartUrlEncode
import java.util.regex.Pattern

internal interface ITextLayerBuilder {
    var style: TextStyle?
    var background: ColorValue?
    var textColor: ColorValue?
    fun font(fontFamily: String, size: Int): Builder
    fun font(fontFamily: String, size: Any): Builder
    fun style(style: TextStyle): Builder
    fun style(style: TextStyle.Builder.() -> Unit): Builder
    fun background(background: ColorValue): Builder
    fun textColor(textColor: ColorValue): Builder
    fun background(color: ColorValue.Builder.() -> Unit): Builder
    fun textColor(color: ColorValue.Builder.() -> Unit): Builder
    fun build(): BaseTextLayerSource
}

open class BaseTextLayerSource internal constructor(
    type: String,
    value: String,
    fontFamily: String? = null,
    fontSize: Any? = null,
    style: TextStyle? = null,
    background: ColorValue? = null,
    textColor: ColorValue? = null
) :
    LayerSource(
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
            background?.let { backgroundParam(it) },
            textColor?.let { ColorParam(it) }
        )
    ) {

    class Builder(
        private val type: String,
        private val value: String
    ) : ITextLayerBuilder {

        var fontFamily: String? = null
        var fontSize: Any? = null
        override var style: TextStyle? = null
        override var background: ColorValue? = null
        override var textColor: ColorValue? = null

        override fun font(fontFamily: String, size: Int) = font(fontFamily, size as Any)
        override fun font(fontFamily: String, size: Any) = apply {
            this.fontFamily = fontFamily
            this.fontSize = size
        }

        override fun style(style: TextStyle) = apply { this.style = style }
        override fun style(style: TextStyle.Builder.() -> Unit) = apply {
            val builder = TextStyle.Builder()
            builder.style()
            style(builder.build())
        }

        override fun background(background: ColorValue) = apply { this.background = background }
        override fun textColor(textColor: ColorValue) = apply { this.textColor = textColor }

        override fun background(color: ColorValue.Builder.() -> Unit): Builder {
            val builder = ColorValue.Builder()
            builder.color()
            return background(builder.build())
        }

        override fun textColor(color: ColorValue.Builder.() -> Unit): Builder {
            val builder = ColorValue.Builder()
            builder.color()
            return textColor(builder.build())
        }

        override fun build() = BaseTextLayerSource(type, value, fontFamily, fontSize, style, background, textColor)
    }
}

class TextLayerSource(
    text: String,
    fontFamily: String,
    fontSize: Any,
    style: TextStyle? = null,
    background: ColorValue? = null,
    textColor: ColorValue? = null
) : BaseTextLayerSource("text", encode(text), fontFamily, fontSize, style, background, textColor) {
    class Builder internal constructor(
        text: String,
        fontFamily: String,
        fontSize: Any,
        b: BaseTextLayerSource.Builder = Builder("text", encode(text))
    ) : ITextLayerBuilder by b {
        init {
            b.font(fontFamily, fontSize)
        }
    }
}

class SubtitlesLayerSource(
    publicId: String,
    style: TextStyle? = null,
    background: ColorValue? = null,
    textColor: ColorValue? = null
) : BaseTextLayerSource("subtitles", publicId, null, null, style, background, textColor) {
    class Builder internal constructor(
        private val publicId: String,
        b: BaseTextLayerSource.Builder = Builder("subtitles", publicId)
    ) : ITextLayerBuilder by b
}


class TextStyle(
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontAntialias: FontAntialias? = null,
    fontHinting: FontHinting? = null,
    textDecoration: FontDecoration? = null,
    textAlign: TextAlign? = null,
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
        textAlign,
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
        private var textDecoration: FontDecoration? = null
        private var textAlign: TextAlign? = null
        private var stroke: Stroke? = null
        private var letterSpacing: Any? = null
        private var lineSpacing: Any? = null

        fun fontWeight(fontWeight: FontWeight) = apply { this.fontWeight = fontWeight }
        fun fontStyle(fontStyle: FontStyle) = apply { this.fontStyle = fontStyle }
        fun fontAntialias(fontAntialias: FontAntialias) = apply { this.fontAntialias = fontAntialias }
        fun fontHinting(fontHinting: FontHinting) = apply { this.fontHinting = fontHinting }
        fun textDecoration(textDecoration: FontDecoration) = apply { this.textDecoration = textDecoration }
        fun textAlign(textAlign: TextAlign) = apply { this.textAlign = textAlign }
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
            textAlign,
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

enum class FontDecoration(private val value: String) {
    NORMAL("normal"),
    UNDERLINE("underline"),
    STRIKETHROUGH("strikethrough");

    override fun toString(): String {
        return value
    }
}

enum class TextAlign(private val value: String) {
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