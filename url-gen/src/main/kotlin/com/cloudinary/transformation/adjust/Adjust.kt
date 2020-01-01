package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamsAction
import com.cloudinary.transformation.cldToActionMap
import com.cloudinary.transformation.effect.Effect

open class Adjust private constructor(params: Map<String, Param>) : ParamsAction<Adjust>(params) {
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

        fun improve(blend: Int? = null, improve: (Improve.Builder.() -> Unit)? = null): Improve {
            val builder = Improve.Builder()
            blend?.let { builder.blend(it) }
            improve?.let { builder.it() }
            return builder.build()
        }

        fun vibrance(level: Int? = null, vibrance: (Vibrance.Builder.() -> Unit)? = null): Vibrance {
            val builder = Vibrance.Builder()
            level?.let { builder.level(it) }
            vibrance?.let { builder.it() }
            return builder.build()
        }

        fun autoColor(level: Int? = null, autoColor: (AutoColor.Builder.() -> Unit)? = null): AutoColor {
            val builder = AutoColor.Builder()
            level?.let { builder.level(it) }
            autoColor?.let { builder.it() }
            return builder.build()
        }

        fun autoBrightness(level: Int? = null, autoBrightness: (AutoBrightness.Builder.() -> Unit)? = null):
                AutoBrightness {
            val builder = AutoBrightness.Builder()
            level?.let { builder.level(it) }
            autoBrightness?.let { builder.it() }
            return builder.build()
        }

        fun brightness(level: Int? = null, brightness: (Brightness.Builder.() -> Unit)? = null): Brightness {
            val builder = Brightness.Builder()
            level?.let { builder.level(it) }
            brightness?.let { builder.it() }
            return builder.build()
        }

        fun autoContrast(level: Int? = null, autoContrast: (AutoContrast.Builder.() -> Unit)? = null):
                AutoContrast {
            val builder = AutoContrast.Builder()
            level?.let { builder.level(it) }
            autoContrast?.let { builder.it() }
            return builder.build()
        }

        fun unsharpMask(level: Int? = null, unsharpMask: (UnsharpMask.Builder.() -> Unit)? = null):
                UnsharpMask {
            val builder = UnsharpMask.Builder()
            level?.let { builder.level(it) }
            unsharpMask?.let { builder.it() }
            return builder.build()
        }

        fun viesusCorrect(): ViesusCorrect =
            ViesusCorrect()

        fun brightnessHSB(level: Int? = null, brightnessHSB: (BrightnessHSB.Builder.() -> Unit)? = null):
                BrightnessHSB {
            val builder = BrightnessHSB.Builder()
            level?.let { builder.level(it) }
            brightnessHSB?.let { builder.it() }
            return builder.build()
        }

        fun replaceColor(replaceColor: (ReplaceColor.Builder.() -> Unit)? = null):
                ReplaceColor {
            val builder = ReplaceColor.Builder()
            replaceColor?.let { builder.it() }
            return builder.build()
        }

        fun hue(level: Int? = null, hue: (Hue.Builder.() -> Unit)? = null): Hue {
            val builder = Hue.Builder()
            level?.let { builder.level(it) }
            hue?.let { builder.it() }
            return builder.build()
        }

        fun gamma(level: Int? = null, gamma: (Gamma.Builder.() -> Unit)? = null): Gamma {
            val builder = Gamma.Builder()
            level?.let { builder.level(it) }
            gamma?.let { builder.it() }
            return builder.build()
        }

        fun contrast(level: Int? = null, contrast: (Contrast.Builder.() -> Unit)? = null): Contrast {
            val builder = Contrast.Builder()
            level?.let { builder.level(it) }
            contrast?.let { builder.it() }
            return builder.build()
        }

        fun blue(level: Int? = null, blue: (Blue.Builder.() -> Unit)? = null): Blue {
            val builder = Blue.Builder()
            level?.let { builder.level(it) }
            blue?.let { builder.it() }
            return builder.build()
        }

        fun opacityThreshold(
            level: Int? = null,
            opacityThreshold: (OpacityThreshold.Builder.() -> Unit)? =
                null
        ): OpacityThreshold {
            val builder = OpacityThreshold.Builder()
            level?.let { builder.level(it) }
            opacityThreshold?.let { builder.it() }
            return builder.build()
        }

        fun green(level: Int? = null, green: (Green.Builder.() -> Unit)? = null): Green {
            val builder = Green.Builder()
            level?.let { builder.level(it) }
            green?.let { builder.it() }
            return builder.build()
        }

        fun saturation(level: Int? = null, saturation: (Saturation.Builder.() -> Unit)? = null): Saturation {
            val builder = Saturation.Builder()
            level?.let { builder.level(it) }
            saturation?.let { builder.it() }
            return builder.build()
        }

        fun red(level: Int? = null, red: (Red.Builder.() -> Unit)? = null): Red {
            val builder = Red.Builder()
            level?.let { builder.level(it) }
            red?.let { builder.it() }
            return builder.build()
        }

        fun sharpen(strength: Int? = null, sharpen: (Sharpen.Builder.() -> Unit)? = null): Sharpen {
            val builder = Sharpen.Builder()
            strength?.let { builder.strength(it) }
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