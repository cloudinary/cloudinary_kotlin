package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.expression.Expression

abstract class Adjust : Action {
    companion object {
        fun opacity(level: Int) = Opacity(level)
        fun opacity(level: Expression) = Opacity(level)
        fun opacity(options: (Opacity.Builder.() -> Unit)? = null) = buildAdjust(Opacity.Builder(), options)

        fun tint(options: String? = null) = Tint(options)

        fun vibrance(options: (Vibrance.Builder.() -> Unit)? = null) = buildAdjust(Vibrance.Builder(), options)

        fun autoColor(options: (AutoColor.Builder.() -> Unit)? = null) = buildAdjust(AutoColor.Builder(), options)

        fun brightness(options: (Brightness.Builder.() -> Unit)? = null) = buildAdjust(Brightness.Builder(), options)

        fun autoBrightness(options: (AutoBrightness.Builder.() -> Unit)? = null) =
            buildAdjust(AutoBrightness.Builder(), options)

        fun brightnessHSB(options: (BrightnessHSB.Builder.() -> Unit)? = null) =
            buildAdjust(BrightnessHSB.Builder(), options)

        fun autoContrast(options: (AutoContrast.Builder.() -> Unit)? = null) =
            buildAdjust(AutoContrast.Builder(), options)

        fun unsharpMask(options: (UnsharpMask.Builder.() -> Unit)? = null) = buildAdjust(UnsharpMask.Builder(), options)

        fun viesusCorrect(options: (ViesusCorrect.Builder.() -> Unit)? = null) =
            buildAdjust(ViesusCorrect.Builder(), options)

        fun hue(options: (Hue.Builder.() -> Unit)? = null) = buildAdjust(Hue.Builder(), options)

        fun gamma(options: (Gamma.Builder.() -> Unit)? = null) = buildAdjust(Gamma.Builder(), options)

        fun contrast(options: (Contrast.Builder.() -> Unit)? = null) = buildAdjust(Contrast.Builder(), options)

        fun blue(options: (Blue.Builder.() -> Unit)? = null) = buildAdjust(Blue.Builder(), options)

        fun green(options: (Green.Builder.() -> Unit)? = null) = buildAdjust(Green.Builder(), options)

        fun red(options: (Red.Builder.() -> Unit)? = null) = buildAdjust(Red.Builder(), options)

        fun opacityThreshold(options: (OpacityThreshold.Builder.() -> Unit)? = null) =
            buildAdjust(OpacityThreshold.Builder(), options)

        fun saturation(options: (Saturation.Builder.() -> Unit)? = null) = buildAdjust(Saturation.Builder(), options)

        fun sharpen(options: (Sharpen.Builder.() -> Unit)? = null) = buildAdjust(Sharpen.Builder(), options)

        fun replaceColor(toColor: Color, options: (ReplaceColor.Builder.() -> Unit)? = null) =
            buildAdjust(ReplaceColor.Builder(toColor), options)

        fun recolor(colorMatrix: List<List<Float>>) = Recolor(colorMatrix)

        fun recolor(vararg colorMatrix: Float) = Recolor(colorMatrix)

        fun fillLight(options: (FillLight.Builder.() -> Unit)? = null) = buildAdjust(FillLight.Builder(), options)

        fun improve(options: (Improve.Builder.() -> Unit)? = null) = buildAdjust(Improve.Builder(), options)

        fun by3dLut(publicId: String) = By3dLut(publicId)
    }
}

interface AdjustBuilder : TransformationComponentBuilder

private fun <T : AdjustBuilder> buildAdjust(builder: T, options: (T.() -> Unit)?): Adjust {
    options?.let { builder.it() }
    return builder.build() as Adjust
}