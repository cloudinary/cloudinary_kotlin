package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.Transformation
import com.cloudinary.util.cldSmartUrlEncode
import java.util.regex.Pattern

interface LayerSource {
    val transformation: Transformation?

    companion object {
        fun text(text: String, options: (TextSource.Builder.() -> Unit)? = null) = text(text as Any, options)

        fun text(text: Any, options: (TextSource.Builder.() -> Unit)? = null): TextSource {
            val builder = TextSource.Builder(text)
            options?.let { builder.it() }
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

interface BaseVideoSource : LayerSource

class TextStyle internal constructor(
    private val fontFamily: Any,
    private val fontSize: Any,
    private val fontWeight: FontWeight? = null,
    private val fontStyle: FontStyle? = null,
    private val fontAntialias: FontAntialias? = null,
    private val fontHinting: FontHinting? = null,
    private val textDecoration: TextDecoration? = null,
    private val textAlignment: TextAlignment? = null,
    private val stroke: Stroke? = null,
    private val letterSpacing: Any? = null,
    private val lineSpacing: Any? = null
) {

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
            stroke,
            letterSpacing?.let { "letter_spacing_$letterSpacing" },
            lineSpacing?.let { "line_spacing_$lineSpacing" }
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
        private var stroke: Stroke? = null
        private var letterSpacing: Any? = null
        private var lineSpacing: Any? = null

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

enum class FontWeight(private val value: String) {
    NORMAL("normal"),
    BOLD("bold"),
    THIN("thin"),
    LIGHT("light");

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