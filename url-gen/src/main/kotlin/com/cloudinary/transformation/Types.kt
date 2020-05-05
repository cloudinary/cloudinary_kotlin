package com.cloudinary.transformation

import com.cloudinary.util.cldRanged
import com.cloudinary.util.cldToString

internal fun widthParam(w: Any) = Param("width", "w", ParamValue(w))
internal fun heightParam(h: Any) = Param("height", "h", ParamValue(h))
internal fun aspectRatioParam(ar: Any) = Param("aspect_ratio", "ar", ParamValue(ar))
internal fun dprParam(dpr: Any) = Param("dpr", "dpr", ParamValue(dpr))
internal fun xParam(x: Any?) = x?.run { Param("x", "x", ParamValue(x)) }
internal fun yParam(y: Any?) = y?.run { Param("y", "y", ParamValue(y)) }
internal fun zoomParam(zoom: Any) = Param("zoom", "z", ParamValue(zoom))
internal fun cropParam(cropMode: Any) = Param("crop", "c", ParamValue(cropMode))

internal fun Any.cldAsWidth() = widthParam(this)
internal fun Any.cldAsHeight() = heightParam(this)
internal fun Any.cldAsAspectRatio() = aspectRatioParam(this)
internal fun Any.cldAsDpr() = dprParam(this)
internal fun Any.cldAsX() = xParam(this)
internal fun Any.cldAsY() = yParam(this)
internal fun Any.cldAsZoom() = zoomParam(this)
internal fun Any.cldAsCrop() = cropParam(this)
internal fun Any.cldAsPageParam() = pageParam(this)

internal fun pageParam(page: Any) = Param("page", "pg", ParamValue(page))


internal fun backgroundParam(value: ParamValue) = Param("background", "b", value)

class ColorParam(color: Color) : Param("color", "co", color)

internal class Range(private val from: Int? = null, private val to: Int? = null) {
    override fun toString() =
        if (from == to) from!!.cldToString() else "${(from?.cldToString() ?: "")}-${(to?.cldToString()
            ?: "")}"

    companion object {
        fun from(from: Int) = Range(from = from)
        fun to(to: Int) = Range(to = to)
        fun fromTo(from: Int, to: Int) = Range(from, to)
        fun single(num: Int) = Range(num, num)
    }
}

class EagerTransformation(val transformation: Transformation, val format: String? = null)

class Format(private val value: String) {
    companion object {
        fun auto() = Format("auto")
        fun jpg() = Format("jpg")
        fun webp() = Format("webp")
        fun jp2() = Format("jp2")
        fun png() = Format("png")
    }

    override fun toString() = value
    fun asAction() = GenericAction(Param("fetch_format", "f", value))
}

enum class QualityType(private val value: String) {
    AUTO("auto"),
    JPEG_MINI("jpegmini");

    override fun toString() = value
}

enum class ChromaSubSampling(private val value: String) {
    C_444("444"),
    C_420("420");

    override fun toString() = value
}

enum class AutoQuality(private val value: String) {
    BEST("best"),
    GOOD("good"),
    ECO("eco"),
    LOW("low");

    override fun toString() = value
}

class Dpr private constructor(value: ParamValue) :
    GenericAction(Param("dpr", "dpr", value)) {

    companion object {
        fun fixed(dpr: Number) = Dpr(ParamValue(dpr))
        fun auto() = Dpr(ParamValue("auto"))
    }
}

class Quality private constructor(value: ParamValue, flag: FlagKey? = null) :
    GenericAction(
        listOfNotNull(
            Param("quality", "q", value),
            flag?.asParam()
        ).cldToActionMap()
    ) {

    constructor(level: Int) : this(ParamValue(level))

    companion object {
        fun auto(quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(QualityType.AUTO)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun jpegmini(quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(QualityType.JPEG_MINI)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun fixed(level: Int, quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(level = level)
            quality?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder internal constructor(private val level: Any? = null, private val type: QualityType? = null) :
        TransformationComponentBuilder {
        constructor(level: Int) : this(level, null)
        constructor(type: QualityType) : this(null, type)

        private var maxQuantization: Any? = null // Int
        private var chromaSubSampling: ChromaSubSampling? = null
        private var preset: AutoQuality? = null
        private var anyFormat: Boolean? = null

//        fun level(level: Int) = apply { this.level = level.cldRanged(1, 100) }
//        fun setLevel(level: Any) = apply { this.level = level.cldRanged(1, 100) }

        fun maxQuantization(maxQuantization: Any) =
            apply { this.maxQuantization = maxQuantization.cldRanged(1, 100) }

        fun maxQuantization(maxQuantization: Int) =
            apply { this.maxQuantization = maxQuantization.cldRanged(1, 100) }


        fun chromaSubSampling(chromaSubSampling: ChromaSubSampling) =
            apply { this.chromaSubSampling = chromaSubSampling }

        fun preset(preset: AutoQuality) = apply { this.preset = preset }

        fun anyFormat(anyFormat: Boolean) = apply { this.anyFormat = anyFormat }

        override fun build() = Quality(
            ParamValue(
                listOfNotNull(
                    type,
                    level,
                    maxQuantization?.let { NamedValue("qmax", maxQuantization!!, "_") },
                    chromaSubSampling,
                    preset
                )
            ), if (anyFormat == true) FlagKey.AnyFormat() else null
        )
    }
}

