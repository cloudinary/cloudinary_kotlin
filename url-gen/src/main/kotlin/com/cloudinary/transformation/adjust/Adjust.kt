package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.CParamsAction
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.effect.innerEffectAction
import com.cloudinary.util.cldRanged

class Adjust(private val action: Action) : Action by action {

    companion object {
        fun opacity(level: Int) = Adjust(CParamsAction(Param("opacity", "o", level)))

        fun improve(blend: Int? = null, options: (ImproveBuilder.() -> Unit)? = null): Adjust {
            val builder = ImproveBuilder()
            blend?.let { builder.blend(blend) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun vibrance(level: Int? = null) = adjustEffect("vibrance", level?.cldRanged(-100, 100))

        fun autoColor(level: Int? = null) = adjustEffect("auto_color", level?.cldRanged(0, 100))

        fun brightness(level: Int? = null) = adjustEffect("brightness", level?.cldRanged(-99, 100))

        fun autoBrightness(level: Int? = null) = adjustEffect("auto_brightness", level?.cldRanged(0, 100))

        fun brightnessHSB(level: Int? = null) = adjustEffect("brightness_hsb", level?.cldRanged(-99, 100))

        fun autoContrast(level: Int? = null) = adjustEffect("auto_contrast", level?.cldRanged(0, 100))

        fun unsharpMask(level: Int? = null) = adjustEffect("unsharp_mask", level?.cldRanged(1, 2000))

        fun viesusCorrect() = adjustEffect("viesus_correct")

        fun replaceColor(to: Color, options: (ReplaceColorBuilder.() -> Unit)? = null): Adjust {
            val builder = ReplaceColorBuilder(to)
            options?.let { builder.it() }
            return builder.build()
        }

        fun hue(level: Int? = null) = adjustEffect("hue", level?.cldRanged(-100, 100))

        fun gamma(level: Int? = null) = adjustEffect("gamma", level) // TODO range

        fun contrast(level: Int? = null) = adjustEffect("contrast", level?.cldRanged(-100, 100))

        fun blue(level: Int? = null) = adjustEffect("blue", level?.cldRanged(-100, 100))

        fun green(level: Int? = null) = adjustEffect("green", level?.cldRanged(-100, 100))

        fun red(level: Int? = null) = adjustEffect("red", level?.cldRanged(-100, 100))

        fun opacityThreshold(level: Int? = null) = adjustEffect("opacity_threshold", level?.cldRanged(1, 100))

        fun saturation(level: Int? = null) = adjustEffect("saturation", level?.cldRanged(-100, 100))

        fun sharpen(strength: Int? = null) = adjustEffect("sharpen", strength?.cldRanged(1, 2000))

        fun fillLight(options: (FillLightBuilder.() -> Unit)? = null): Adjust {
            val builder = FillLightBuilder()
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

internal fun adjustEffect(name: String, vararg values: Any?) = Adjust(innerEffectAction(name, *values))
