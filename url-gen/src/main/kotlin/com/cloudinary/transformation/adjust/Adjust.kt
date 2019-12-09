package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.cldToActionMap
import com.cloudinary.transformation.effect.Effect

open class Adjust private constructor(params: Map<String, Param>) : Action<Adjust>(params) {
    constructor(params: Collection<Param>) : this(params.cldToActionMap())
    constructor(param: Param) : this(listOf(param))
    constructor (
        name: String,
        values: List<Any> = emptyList(),
        params: Collection<Param> = emptyList()
    ) : this(Effect(name, values, params).params.values)

    constructor (name: String, value: Any?) : this(name, listOfNotNull(value))

    override fun create(params: Map<String, Param>) =
        Adjust(params)

    companion object {
        fun opacity(level: Int, opacity: (Opacity.Builder.() -> Unit)? = null) = opacity(level as Any, opacity)

        fun opacity(level: Any, opacity: (Opacity.Builder.() -> Unit)? = null): Opacity {
            val builder = Opacity.Builder(level)
            opacity?.let { builder.it() }
            return builder.build()
        }

        fun improve(improve: (Improve.Builder.() -> Unit)? = null): Improve {
            val builder = Improve.Builder()
            improve?.let { builder.it() }
            return builder.build()
        }

        fun vibrance(vibrance: (Vibrance.Builder.() -> Unit)? = null): Vibrance {
            val builder = Vibrance.Builder()
            vibrance?.let { builder.it() }
            return builder.build()
        }

        fun autoColor(autoColor: (AutoColor.Builder.() -> Unit)? = null): AutoColor {
            val builder = AutoColor.Builder()
            autoColor?.let { builder.it() }
            return builder.build()
        }

        fun autoBrightness(autoBrightness: (AutoBrightness.Builder.() -> Unit)? = null):
                AutoBrightness {
            val builder = AutoBrightness.Builder()
            autoBrightness?.let { builder.it() }
            return builder.build()
        }

        fun brightness(brightness: (Brightness.Builder.() -> Unit)? = null): Brightness {
            val builder = Brightness.Builder()
            brightness?.let { builder.it() }
            return builder.build()
        }

        fun autoContrast(autoContrast: (AutoContrast.Builder.() -> Unit)? = null):
                AutoContrast {
            val builder = AutoContrast.Builder()
            autoContrast?.let { builder.it() }
            return builder.build()
        }

        fun unsharpMask(unsharpMask: (UnsharpMask.Builder.() -> Unit)? = null):
                UnsharpMask {
            val builder = UnsharpMask.Builder()
            unsharpMask?.let { builder.it() }
            return builder.build()
        }

        fun viesusCorrect(): ViesusCorrect =
            ViesusCorrect()

        fun brightnessHSB(brightnessHSB: (BrightnessHSB.Builder.() -> Unit)? = null):
                BrightnessHSB {
            val builder = BrightnessHSB.Builder()
            brightnessHSB?.let { builder.it() }
            return builder.build()
        }

        fun replaceColor(replaceColor: (ReplaceColor.Builder.() -> Unit)? = null):
                ReplaceColor {
            val builder = ReplaceColor.Builder()
            replaceColor?.let { builder.it() }
            return builder.build()
        }

        fun hue(hue: (Hue.Builder.() -> Unit)? = null): Hue {
            val builder = Hue.Builder()
            hue?.let { builder.it() }
            return builder.build()
        }

        fun gamma(gamma: (Gamma.Builder.() -> Unit)? = null): Gamma {
            val builder = Gamma.Builder()
            gamma?.let { builder.it() }
            return builder.build()
        }

        fun contrast(contrast: (Contrast.Builder.() -> Unit)? = null): Contrast {
            val builder = Contrast.Builder()
            contrast?.let { builder.it() }
            return builder.build()
        }

        fun blue(blue: (Blue.Builder.() -> Unit)? = null): Blue {
            val builder = Blue.Builder()
            blue?.let { builder.it() }
            return builder.build()
        }

        fun opacityThreshold(
            opacityThreshold: (OpacityThreshold.Builder.() -> Unit)? =
                null
        ): OpacityThreshold {
            val builder = OpacityThreshold.Builder()
            opacityThreshold?.let { builder.it() }
            return builder.build()
        }

        fun green(green: (Green.Builder.() -> Unit)? = null): Green {
            val builder = Green.Builder()
            green?.let { builder.it() }
            return builder.build()
        }

        fun saturation(saturation: (Saturation.Builder.() -> Unit)? = null): Saturation {
            val builder = Saturation.Builder()
            saturation?.let { builder.it() }
            return builder.build()
        }

        fun red(red: (Red.Builder.() -> Unit)? = null): Red {
            val builder = Red.Builder()
            red?.let { builder.it() }
            return builder.build()
        }

        fun sharpen(sharpen: (Sharpen.Builder.() -> Unit)? = null): Sharpen {
            val builder = Sharpen.Builder()
            sharpen?.let { builder.it() }
            return builder.build()
        }

        fun fillLight(fillLight: (FillLight.Builder.() -> Unit)? = null): FillLight {
            val builder = FillLight.Builder()
            fillLight?.let { builder.it() }
            return builder.build()
        }
    }
}