package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldRanged

class DefaultImage(private val publicId: String) : Delivery() {
    override fun toString() = "d_$publicId"
}

class Density(private val density: Int) : Delivery() {
    override fun toString() = "dn_$density"
}

class ColorSpace(private val colorSpace: ColorSpaceEnum) {
    override fun toString(): String {
        return "cs_$colorSpace"
    }
}

class Dpr private constructor(private val dpr: Any) {
    override fun toString(): String {
        return "dpr_$dpr"
    }

    companion object {
        fun auto() = Dpr("auto")
        fun fromFloat(dpr: Float) = Dpr(dpr)
        fun fromExpression(dpr: Expression) = Dpr(dpr)
        fun fromString(dpr: String) = Dpr(dpr)
    }
}

class Format(
    private val format: FormatType,
    private val lossy: Boolean = false,
    private val progressive: Boolean = false,
    private val progressiveMode: ProgressiveMode? = null
) : Delivery() {
    override fun toString(): String {
        val lossyStr = if (lossy) ",fl_lossy" else ""
        val progressiveStr = if (progressive || progressiveMode != null)
            ",fl_progressive".joinWithValues(progressiveMode)
        else
            ""

        return "f_$format$lossyStr$progressiveStr"
    }

    class Builder(private val format: FormatType) : TransformationComponentBuilder {
        private var lossy: Boolean = false
        private var progressive: Boolean = false
        private var progressiveMode: ProgressiveMode? = null


        fun lossy(lossy: Boolean) = apply { this.lossy = lossy }
        fun progressive(progressiveMode: ProgressiveMode? = null) = apply {
            this.progressive = true
            this.progressiveMode = progressiveMode
        }

        override fun build(): Format {
            return Format(format, lossy, progressive, progressiveMode)
        }
    }
}

class Quality(
    private val level: Any,
    private val chromaSubSampling: ChromaSubSampling? = null,
    private val quantization: Any? = null,
    private val anyFormat: Boolean = false
) : Delivery() {

    init {
        quantization?.cldRanged(1, 100)
    }

    override fun toString(): String {
        val anyFormatStr = if (anyFormat) "fl_any_format," else ""
        return "${anyFormatStr}q_$level".joinWithValues(chromaSubSampling, quantization?.let { "qmax_$it" })
    }

    companion object {
        fun auto(options: (Builder.() -> Unit)? = null) = build(QualityType.auto(), options)
        fun eco(options: (Builder.() -> Unit)? = null) = build(QualityType.eco(), options)
        fun good(options: (Builder.() -> Unit)? = null) = build(QualityType.good(), options)
        fun best(options: (Builder.() -> Unit)? = null) = build(QualityType.best(), options)
        fun low(options: (Builder.() -> Unit)? = null) = build(QualityType.low(), options)
        fun jpegminiHigh(options: (Builder.() -> Unit)? = null) = build(QualityType.jpegminiHigh(), options)
        fun jpegminiMedium(options: (Builder.() -> Unit)? = null) = build(QualityType.jpegminiMedium(), options)
        fun jpegminiBest(options: (Builder.() -> Unit)? = null) = build(QualityType.jpegminiBest(), options)

        private fun build(level: Any, options: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(level)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder internal constructor(private val level: Any) : TransformationComponentBuilder {

        private var quantization: Any? = null
        private var chromaSubSampling: ChromaSubSampling? = null
        private var anyFormat: Boolean = false

        fun quantization(quantization: Any) = apply { this.quantization = quantization }
        fun quantization(quantization: Int) = quantization(quantization as Any)

        fun chromaSubSampling(chromaSubSampling: ChromaSubSampling) =
            apply { this.chromaSubSampling = chromaSubSampling }

        fun anyFormat(anyFormat: Boolean = true) = apply { this.anyFormat = anyFormat }

        override fun build() = Quality(level, chromaSubSampling, quantization, anyFormat)
    }
}

enum class ChromaSubSampling(private val value: String) {
    C_444("444"),
    C_420("420");

    override fun toString() = value
}

enum class JpegMini(private val level: Int) {
    HIGH(1),
    MEDIUM(2),
    BEST(0), ;

    override fun toString() = level.toString()
}

sealed class QualityType(private val value: String) {
    class auto : QualityType("auto")
    class eco : QualityType("auto:eco")
    class good : QualityType("auto:good")
    class best : QualityType("auto:best")
    class low : QualityType("auto:low")
    class jpegminiHigh : QualityType("jpegmini:1")
    class jpegminiMedium : QualityType("jpegmini:2")
    class jpegminiBest : QualityType("jpegmini:0")

    override fun toString(): String {
        return value
    }
}

sealed class ProgressiveMode(private val value: String) {
    class none : ProgressiveMode("none")
    class semi : ProgressiveMode("semi")
    class steep : ProgressiveMode("steep")

    override fun toString(): String {
        return value
    }
}