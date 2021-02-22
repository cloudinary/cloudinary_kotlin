package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Format
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldRanged

class DefaultImage(private val publicIdWithExtension: String) : Delivery() {
    override fun toString() = "d_${publicIdWithExtension.cldEncodePublicId()}"
}

class Density(private val density: Any) : Delivery() {
    override fun toString() = "dn_$density"
}

class ColorSpaceAction(private val colorSpace: ColorSpace) : Delivery() {
    override fun toString(): String {
        return "cs_$colorSpace"
    }
}

class ColorSpaceFromICC(private val publicId: String) : Delivery() {
    override fun toString(): String {
        return "cs_icc:${publicId.cldEncodePublicId()}"
    }
}

class Dpr private constructor(private val dpr: Any) : Delivery() {
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

class DeliveryFormat(
    private val format: Format,
    private val lossy: Boolean? = null,
    private val progressive: Progressive? = null,
    private val preserveTransparency: Boolean? = null

) : Delivery() {
    override fun toString(): String {
        val lossyStr = if (lossy == true) "fl_lossy" else null
        val preserveTransparencyStr = if (preserveTransparency == true) "fl_preserve_transparency" else null
        val progressiveStr = progressive?.toString()

        return "f_$format".joinWithValues(lossyStr, preserveTransparencyStr, progressiveStr, separator = ",")
    }

    class Builder(private val format: Format) : TransformationComponentBuilder {
        private var lossy: Boolean? = null
        private var progressive: Progressive? = null
        private var preserveTransparency: Boolean? = null

        fun lossy(lossy: Boolean? = true) = apply { this.lossy = lossy }
        fun progressive(progressive: Progressive) = apply {
            this.progressive = progressive
        }

        fun preserveTransparency() = apply { this.preserveTransparency = true }

        override fun build(): DeliveryFormat {
            return DeliveryFormat(format, lossy, progressive, preserveTransparency)
        }
    }
}

class QualityAction private constructor(
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

    class Builder internal constructor(private val level: Any) : TransformationComponentBuilder {

        private var quantization: Any? = null
        private var chromaSubSampling: ChromaSubSampling? = null
        private var anyFormat: Boolean = false

        fun quantization(quantization: Any) = apply { this.quantization = quantization }
        fun quantization(quantization: Int) = quantization(quantization as Any)

        fun chromaSubSampling(chromaSubSampling: ChromaSubSampling) =
            apply { this.chromaSubSampling = chromaSubSampling }

        fun anyFormat(anyFormat: Boolean = true) = apply { this.anyFormat = anyFormat }

        override fun build() = QualityAction(level, chromaSubSampling, quantization, anyFormat)
    }
}

class ChromaSubSampling private constructor(private val value: String) {
    companion object {
        private val chroma444 = ChromaSubSampling("444")
        fun chroma444() = chroma444
        private val chroma420 = ChromaSubSampling("420")
        fun chroma420() = chroma420
    }

    override fun toString() = value
}

internal class Quality(private vararg val values: String) {

    companion object {
        private val auto = Quality("auto")
        fun auto() = auto
        private val autoEco = Quality("auto", "eco")
        fun autoEco() = autoEco
        private val autoGood = Quality("auto", "good")
        fun autoGood() = autoGood
        private val autoBest = Quality("auto", "best")
        fun autoBest() = autoBest
        private val autoLow = Quality("auto", "low")
        fun autoLow() = autoLow
        private val jpegmini = Quality("jpegmini")
        fun jpegmini() = jpegmini
        private val jpegminiHigh = Quality("jpegmini", "1")
        fun jpegminiHigh() = jpegminiHigh
        private val jpegminiMedium = Quality("jpegmini", "2")
        fun jpegminiMedium() = jpegminiMedium
        private val jpegminiBest = Quality("jpegmini", "0")
        fun jpegminiBest() = jpegminiBest
    }

    override fun toString(): String {
        return values.joinToString(separator = ":")
    }
}

class ProgressiveMode(private val value: String) {
    companion object {
        private val none = ProgressiveMode("none")
        fun none() = none
        private val semi = ProgressiveMode("semi")
        fun semi() = semi
        private val steep = ProgressiveMode("steep")
        fun steep() = steep
    }

    override fun toString(): String {
        return value
    }
}

class ColorSpace(private val value: Any) {

    companion object {
        private val srgb = ColorSpace("srgb")
        fun srgb() = srgb
        private val tinySRgb = ColorSpace("tinysrgb")
        fun tinySRgb() = tinySRgb
        private val cmyk = ColorSpace("cmyk")
        fun cmyk() = cmyk
        private val noCmyk = ColorSpace("no_cmyk")
        fun noCmyk() = noCmyk
        private val keepCmyk = ColorSpace("keep_cmyk")
        fun keepCmyk() = keepCmyk
    }

    override fun toString(): String {
        return value.toString()
    }
}

class Progressive private constructor(private val mode: ProgressiveMode? = null) {
    companion object {
        fun semi() = Progressive(ProgressiveMode.semi())
        fun steep() = Progressive(ProgressiveMode.steep())
        fun none() = Progressive(ProgressiveMode.none())
        fun progressive() = Progressive()
    }

    override fun toString(): String {
        return "fl_progressive".joinWithValues(mode)
    }
}