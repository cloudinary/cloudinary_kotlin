package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.IBaseTransformable
import com.cloudinary.transformation.Param
import com.cloudinary.util.cldSmartUrlEncode
import java.beans.Expression
import java.util.regex.Pattern

interface Source {
    val transformation: IBaseTransformable<*>?

    companion object {
        fun text(text: String, options: (TextSource.Builder.() -> Unit)? = null) = text(text as Any, options)

        fun text(text: Any, options: (TextSource.Builder.() -> Unit)? = null): TextSource {
            val builder = TextSource.Builder(text)
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(text: String, style: Expression, options: (TextSource.Builder.() -> Unit)? = null) =
            text(text, style as Any, options)

        fun text(text: String, style: TextStyle, options: (TextSource.Builder.() -> Unit)? = null) =
            text(text, style as Any, options)

        fun text(text: String, style: String, options: (TextSource.Builder.() -> Unit)? = null) =
            text(text, style as Any, options)

        private fun text(text: String, style: Any, options: (TextSource.Builder.() -> Unit)? = null): TextSource {
            val builder = TextSource.Builder(text)
            options?.let { builder.it() }
            builder.style(style)
            return builder.build()
        }

        fun image(publicId: String, options: (ImageSource.Builder.() -> Unit)? = null): ImageSource {
            val builder = ImageSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }

        fun video(publicId: String, options: (VideoSource.Builder.() -> Unit)? = null): VideoSource {
            val builder = VideoSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }

        fun subtitles(publicId: String, options: (SubtitlesSource.Builder.() -> Unit)? = null): SubtitlesSource {
            val builder = SubtitlesSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(url: String, options: (FetchSource.Builder.() -> Unit)? = null): FetchSource {
            val builder = FetchSource.Builder(url)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    fun extraComponents(): List<Param>
}

interface BaseVideoSource : Source

private fun buildTextStyle(
    fontFamily: Any,
    fontSize: Any, options: (TextStyle.Builder.() -> Unit)? = null
): TextStyle {
    val builder = TextStyle.Builder(fontFamily, fontSize)
    options?.let { builder.it() }
    return builder.build()
}

class TextStyle internal constructor(
    private val fontFamily: Any,
    private val fontSize: Any,
    private val fontWeight: FontWeight? = null,
    private val fontStyle: FontStyle? = null,
    private val fontAntialias: FontAntialias? = null,
    private val fontHinting: FontHinting? = null,
    private val textDecoration: TextDecoration? = null,
    private val textAlignment: TextAlignment? = null,
    private val stroke: Boolean? = null,
    private val letterSpacing: Any? = null,
    private val lineSpacing: Any? = null
) {
    private constructor(style: TextStyle) : this(
        style.fontFamily,
        style.fontSize,
        style.fontWeight,
        style.fontStyle,
        style.fontAntialias,
        style.fontHinting,
        style.textDecoration,
        style.textAlignment,
        style.stroke,
        style.letterSpacing,
        style.lineSpacing
    )

    constructor(
        fontFamily: Any,
        fontSize: Any, options: (Builder.() -> Unit)? = null
    ) : this(buildTextStyle(fontFamily, fontSize, options))

    override fun toString(): String {
        return listOfNotNull(
            fontFamily,
            fontSize,
            fontWeight,
            fontStyle,
            fontAntialias?.let { "antialias_$fontAntialias" },
            fontHinting?.let { "hinting_$fontHinting" },
            textDecoration,
            textAlignment,
            letterSpacing?.let { "letter_spacing_$letterSpacing" },
            lineSpacing?.let { "line_spacing_$lineSpacing" },
            if (stroke == true) "stroke" else null
        ).joinToString("_")
    }

    class Builder(private val fontFamily: Any, private val fontSize: Any) {
        constructor(fontFamily: String, fontSize: Int) : this(fontFamily as Any, fontSize as Any)

        private var fontWeight: FontWeight? = null
        private var fontStyle: FontStyle? = null
        private var fontAntialias: FontAntialias? = null
        private var fontHinting: FontHinting? = null
        private var textDecoration: TextDecoration? = null
        private var textAlignment: TextAlignment? = null
        private var stroke: Boolean? = null
        private var letterSpacing: Any? = null
        private var lineSpacing: Any? = null

        fun fontStyle(fontStyle: FontStyle) = apply { this.fontStyle = fontStyle }
        fun fontAntialias(fontAntialias: FontAntialias) = apply { this.fontAntialias = fontAntialias }
        fun fontHinting(fontHinting: FontHinting) = apply { this.fontHinting = fontHinting }
        fun textDecoration(textDecoration: TextDecoration) = apply { this.textDecoration = textDecoration }
        fun textAlignment(textAlignment: TextAlignment) = apply { this.textAlignment = textAlignment }
        fun stroke(stroke: Boolean = true) = apply { this.stroke = stroke }
        fun letterSpacing(letterSpacing: Any) = apply { this.letterSpacing = letterSpacing }
        fun lineSpacing(lineSpacing: Any) = apply { this.lineSpacing = lineSpacing }
        fun letterSpacing(letterSpacing: Float) = apply { this.letterSpacing = letterSpacing }
        fun lineSpacing(lineSpacing: Float) = apply { this.lineSpacing = lineSpacing }
        fun fontWeight(fontWeight: FontWeight) = apply { this.fontWeight = fontWeight }

        fun build() = TextStyle(
            fontFamily,
            fontSize,
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


class FontWeight private constructor(private val value: String) {
    companion object {
        private val normal = FontWeight("normal")
        fun normal() = normal
        private val bold = FontWeight("bold")
        fun bold() = bold
        private val thin = FontWeight("thin")
        fun thin() = thin
        private val light = FontWeight("light")
        fun light() = light
    }

    override fun toString(): String {
        return value
    }
}

class FontStyle private constructor(private val value: String) {
    companion object {
        private val normal = FontStyle("normal")
        fun normal() = normal
        private val italic = FontStyle("italic")
        fun italic() = italic
    }

    override fun toString(): String {
        return value
    }
}

class TextDecoration private constructor(private val value: String) {
    companion object {
        private val normal = TextDecoration("normal")
        fun normal() = normal
        private val underline = TextDecoration("underline")
        fun underline() = underline
        private val strikethrough = TextDecoration("strikethrough")
        fun strikethrough() = strikethrough
    }

    override fun toString(): String {
        return value
    }
}

class TextAlignment private constructor(private val value: String) {
    companion object {
        private val left = TextAlignment("left")
        fun left() = left
        private val center = TextAlignment("center")
        fun center() = center
        private val right = TextAlignment("right")
        fun right() = right
        private val end = TextAlignment("end")
        fun end() = end
        private val start = TextAlignment("start")
        fun start() = start
        private val justify = TextAlignment("justify")
        fun justify() = justify
    }

    override fun toString(): String {
        return value
    }
}

class FontAntialias private constructor(private val value: String) {
    companion object {
        private val none = FontAntialias("none")
        fun none() = none
        private val gray = FontAntialias("gray")
        fun gray() = gray
        private val subpixel = FontAntialias("subpixel")
        fun subpixel() = subpixel
        private val fast = FontAntialias("fast")
        fun fast() = fast
        private val good = FontAntialias("good")
        fun good() = good
        private val best = FontAntialias("best")
        fun best() = best
    }

    override fun toString(): String {
        return value
    }
}

class FontHinting private constructor(private val value: String) {
    companion object {
        private val none = FontHinting("none")
        fun none() = none
        private val slight = FontHinting("slight")
        fun slight() = slight
        private val medium = FontHinting("medium")
        fun medium() = medium
        private val full = FontHinting("full")
        fun full() = full
    }

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