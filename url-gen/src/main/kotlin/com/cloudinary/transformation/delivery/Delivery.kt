package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRealPositive

class Delivery(private val action: Action) : Action by action {


    companion object {
        fun fallbackImage(publicId: String) = delivery(publicId.cldAsFallbackImage())
        fun density(density: Int) = delivery(density.cldAsDensity())
        fun dpr(dpr: Dpr) = delivery(dpr)
        fun dpr(dpr: Float) = delivery(dpr.cldAsDpr())
        fun dpr(dpr: String) = delivery(dpr.cldAsDpr())
        fun fps(fps: Float) = FpsBuilder().fixed(fps).build()
        fun fps(min: Float, max: Float) = FpsBuilder().min(min).max(max).build()
        fun fps(options: (FpsBuilder.() -> Unit)? = null): Delivery {
            val builder = FpsBuilder()
            options?.let { builder.options() }
            return builder.build()
        }

        fun defaultImage(publicId: String) = delivery(publicId.cldAsDefaultImage())

        fun colorSpace(colorSpace: ColorSpace) = delivery(colorSpace.cldAsColorSpace())

        fun keyframeInterval(seconds: Float) = delivery((seconds.cldRealPositive().cldAsKeyframeInterval()))
    }
}

class Dpr internal constructor(value: Any) : Param("dpr", "dpr", value) {
    companion object {
        fun auto() = Dpr("auto")
    }
}

sealed class ColorSpace(value: Any) : ParamValue(value) {
    constructor(value: String) : this(ParamValue(value))

    object SRgb : ColorSpace("srgb")
    object TinySRgb : ColorSpace("tinysrgb")
    object Cmyk : ColorSpace("cmyk")
    object NoCmyk : ColorSpace("no_cmyk")
    object KeepCmyk : ColorSpace("keep_cmyk")
    class CsIcc(publicId: String) : ColorSpace(
        ParamValue(listOfNotNull("icc", publicId))
    )
}

internal fun delivery(vararg params: Param) = Delivery(ParamsAction(params.toList()))