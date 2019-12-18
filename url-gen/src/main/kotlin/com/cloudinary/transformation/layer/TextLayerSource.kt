package com.cloudinary.transformation.layer

import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.cldAsNonNullSimpleValues
import com.cloudinary.util.cldPrepend
import com.cloudinary.util.cldSmartUrlEncode
import java.util.regex.Pattern

class TextLayerSource(
    text: String,
    fontFamily: String,
    fontSize: Any,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontAntialias: FontAntialias? = null,
    fontHinting: FontHinting? = null,
    textDecoration: FontDecoration? = null,
    textAlign: TextAlign? = null,
    stroke: Stroke? = null,
    letterSpacing: Any? = null,
    lineSpacing: Any? = null
) :
    LayerSource(
        listOfNotNull(
            "text",
            ParamValue(
                listOfNotNull(
                    fontFamily,
                    fontSize,
                    fontWeight,
                    fontStyle,
                    fontAntialias?.cldPrepend("antialias_"),
                    fontHinting?.cldPrepend("hinting_"),
                    textDecoration,
                    textAlign,
                    stroke,
                    letterSpacing?.cldPrepend("letter_spacing_"),
                    lineSpacing?.cldPrepend("line_spacing_")
                ).cldAsNonNullSimpleValues(), "_"
            ),
            encode(text)
        )
    ) {

    class Builder(private val text: String, private val fontFamily: String, private val fontSize: Any) {
        constructor(text: String, fontFamily: String, fontSize: Int) : this(text, fontFamily, fontSize as Any)

        private var fontWeight: FontWeight? = null
        private var fontStyle: FontStyle? = null
        private var fontAntialias: FontAntialias? = null
        private var fontHinting: FontHinting? = null
        private var textDecoration: FontDecoration? = null
        private var textAlign: TextAlign? = null
        private var stroke: Stroke? = null
        private var letterSpacing: Any? = null
        private var lineSpacing: Any? = null

        fun setFontWeight(fontWeight: FontWeight) = apply { this.fontWeight = fontWeight }
        fun setFontStyle(fontStyle: FontStyle) = apply { this.fontStyle = fontStyle }
        fun setFontAntialias(fontAntialias: FontAntialias) = apply { this.fontAntialias = fontAntialias }
        fun setFontHinting(fontHinting: FontHinting) = apply { this.fontHinting = fontHinting }
        fun setTextDecoration(textDecoration: FontDecoration) = apply { this.textDecoration = textDecoration }
        fun setTextAlign(textAlign: TextAlign) = apply { this.textAlign = textAlign }
        fun setStroke(stroke: Stroke) = apply { this.stroke = stroke }
        fun setLetterSpacing(letterSpacing: Any) = apply { this.letterSpacing = letterSpacing }
        fun setLineSpacing(lineSpacing: Any) = apply { this.lineSpacing = lineSpacing }
        fun setLetterSpacing(letterSpacing: Float) = apply { this.letterSpacing = letterSpacing }
        fun setLineSpacing(lineSpacing: Float) = apply { this.lineSpacing = lineSpacing }

        fun build() = TextLayerSource(
            text,
            fontFamily, fontSize,
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