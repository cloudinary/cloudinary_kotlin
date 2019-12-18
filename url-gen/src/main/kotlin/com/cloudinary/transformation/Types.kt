package com.cloudinary.transformation

import com.cloudinary.util.cldRemovePound
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

class ColorValue private constructor(values: List<Any?>) : ParamValue(values.filterNotNull()) {

    internal fun asParam() = ColorParam(this)
    internal fun withoutRgbPrefix(): ColorValue {
        val valueContent = values.first()
        return if (valueContent is NamedValue && valueContent.name == "rgb") ColorValue(
            listOf(SimpleValue(valueContent.value)) + values.subList(
                1,
                values.size
            )
        ) else this
    }

    companion object {
        fun parseString(color: String) = when {
            color.startsWith("#") -> {
                val builder = Builder()
                builder.fromRGB(color)
                builder.build()
            }
            color.startsWith("rgb:") -> ColorValue(color.split(":"))
            else -> {
                val builder = Builder()
                builder.named(color)
                builder.build()
            }
        }
    }

    class Builder {
        private var values = mutableListOf<Any>()

        fun fromRGB(hex: String) = apply { values = mutableListOf(NamedValue("rgb", hex.cldRemovePound())) }
        fun named(name: String) = apply { values = mutableListOf(name) }

        fun build() = ColorValue(values)
    }
}

fun color(color: ColorValue.Builder.() -> Unit): ColorValue {
    val builder = ColorValue.Builder()
    builder.color()
    return builder.build()
}

class ColorParam(color: ColorValue) : Param("color", "co", color)

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