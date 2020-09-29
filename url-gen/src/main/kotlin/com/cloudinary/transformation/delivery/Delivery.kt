package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Expression
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldRealPositive

// TODO review api

class Fps private constructor(private val fixed: Any?, private val min: Any?, private var max: Any?) : Delivery() {

    override fun toString(): String {
        return "fps_".run {
            if (fixed != null)
                "${this}$fixed"
            else
                "${this}${min}".joinWithValues(max ?: "", separator = "-")
        }
    }

    class Builder : TransformationComponentBuilder {
        private var fixed: Any? = null
        private var min: Any? = null
        private var max: Any? = null

        internal fun fixed(fps: Float) = apply { this.fixed = fps }
        fun min(min: Float) = apply { this.min = min }
        fun max(max: Float) = apply { this.max = max }
        fun min(min: Int) = apply { this.min = min }
        fun max(max: Int) = apply { this.max = max }
        fun min(min: Any) = apply { this.min = min }
        fun max(max: Any) = apply { this.max = max }

        override fun build() = Fps(fixed, min, max)
    }
}

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

class KeyframeInterval(private val seconds: Float) {
    init {
        seconds.cldRealPositive()
    }

    override fun toString(): String {
        return "ki_$seconds"
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

abstract class Delivery : Action {
    companion object {
        fun fps(fps: Float) = Fps.Builder().fixed(fps).build()
        fun fps(min: Float, max: Float) = Fps.Builder().min(min).max(max).build()
        fun fps(options: (Fps.Builder.() -> Unit)? = null): Fps {
            val builder = Fps.Builder()
            options?.let { builder.options() }
            return builder.build()
        }


        fun defaultImage(publicId: String) = DefaultImage(publicId)
        fun density(density: Int) = Density(density)
        fun colorSpace(colorSpace: ColorSpaceEnum) = ColorSpace(colorSpace)
        fun keyframeInterval(seconds: Float) = KeyframeInterval(seconds)

        fun dpr(dpr: Dpr) = dpr
        fun dpr(dpr: Float) = Dpr.fromFloat(dpr)
        fun dpr(dpr: Expression) = Dpr.fromExpression(dpr)
        fun dpr(dpr: String) = Dpr.fromString(dpr)
    }
}

sealed class ColorSpaceEnum(private val value: Any) {

    object SRgb : ColorSpaceEnum("srgb")
    object TinySRgb : ColorSpaceEnum("tinysrgb")
    object Cmyk : ColorSpaceEnum("cmyk")
    object NoCmyk : ColorSpaceEnum("no_cmyk")
    object KeepCmyk : ColorSpaceEnum("keep_cmyk")
    class CsIcc(private val publicId: String) : ColorSpaceEnum("icc") {
        override fun toString(): String {
            return "${super.toString()}:$publicId"
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}