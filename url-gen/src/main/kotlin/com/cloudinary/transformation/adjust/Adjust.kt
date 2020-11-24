package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Color
import com.cloudinary.util.cldRanged

abstract class Adjust : Action {
    companion object {
        fun opacity(level: Int) = Opacity(level)

        fun tint(vararg values: Any?) = Tint(*values)

        fun vibrance(level: Int? = null) = Vibrance(level)

        fun autoColor(level: Int? = null) = AutoColor(level)

        fun brightness(level: Int? = null) = Brightness(level)

        fun autoBrightness(level: Int? = null) = AutoBrightness(level)

        fun brightnessHSB(level: Int? = null) = BrightnessHSB(level)

        fun autoContrast(level: Int? = null) = AutoContrast(level)

        fun unsharpMask(level: Int? = null) = UnsharpMask(level)

        fun viesusCorrect() = ViesusCorrect()

        fun hue(level: Int? = null) = Hue(level)

        fun gamma(level: Int? = null) = Gamma(level.cldRanged(-50, 150))

        fun contrast(level: Int? = null) = Contrast(level)

        fun blue(level: Int? = null) = Blue(level)

        fun green(level: Int? = null) = Green(level)

        fun red(level: Int? = null) = Red(level)

        fun opacityThreshold(level: Int? = null) = OpacityThreshold(level)

        fun saturation(level: Int? = null) = Saturation(level)

        fun sharpen(strength: Int? = null) = Sharpen(strength)

        fun replaceColor(to: Color, options: (ReplaceColor.Builder.() -> Unit)? = null): Adjust {
            val builder = ReplaceColor.Builder(to)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fillLight(options: (FillLight.Builder.() -> Unit)? = null): Adjust {
            val builder = FillLight.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun improve(options: (Improve.Builder.() -> Unit)? = null): Adjust {
            val builder = Improve.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

