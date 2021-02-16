package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationComponentBuilder

abstract class Adjust : Action {
    companion object {
        /**
         * Used to define the opacity of an asset.
         * @param level
         */
        fun opacity(level: Int) = Opacity(level)
        /**
         * Blends the image with one or more tint colors at the intensity specified.
         * @param values
         */
        fun tint(vararg values: Any?) = Tint(*values)
        /**
         * Applies a vibrance filter on the image.
         * @param level
         */
        fun vibrance(options: (Vibrance.Builder.() -> Unit)? = null) = buildAdjust(Vibrance.Builder(), options)
        /**
         * Adjusts the color balance and blends the result with the original image.
         * @param level
         */
        fun autoColor(options: (AutoColor.Builder.() -> Unit)? = null) = buildAdjust(AutoColor.Builder(), options)
        /**
         * Adjusts the brightness.
         * @param level
         */
        fun brightness(options: (Brightness.Builder.() -> Unit)? = null) = buildAdjust(Brightness.Builder(), options)
        /**
         * Adjusts the brightness and blends the result with the original image.
         * @param level
         */
        fun autoBrightness(options: (AutoBrightness.Builder.() -> Unit)? = null) =
            buildAdjust(AutoBrightness.Builder(), options)
        /**
         * Adjusts image brightness modulation in HSB to prevent artifacts in some images.
         * @param level
         */
        fun brightnessHSB(options: (BrightnessHSB.Builder.() -> Unit)? = null) =
            buildAdjust(BrightnessHSB.Builder(), options)
        /**
         * Adjusts the contrast and blends the result with the original image.
         * @param level
         */
        fun autoContrast(options: (AutoContrast.Builder.() -> Unit)? = null) =
            buildAdjust(AutoContrast.Builder(), options)
        /**
         * Applies an unsharp mask filter to the image.
         * @param level
         */
        fun unsharpMask(options: (UnsharpMask.Builder.() -> Unit)? = null) = buildAdjust(UnsharpMask.Builder(), options)
        /**
         * Enhances an image to its best visual quality with the Viesus Automatic Image Enhancement add-on.
         * @param
         */
        fun viesusCorrect(options: (ViesusCorrect.Builder.() -> Unit)? = null) =
            buildAdjust(ViesusCorrect.Builder(), options)
        /**
         * Adjusts the image's hue.
         * @param level
         */
        fun hue(options: (Hue.Builder.() -> Unit)? = null) = buildAdjust(Hue.Builder(), options)
        /**
         * Adjusts the gamma level.
         * @param level
         */
        fun gamma(options: (Gamma.Builder.() -> Unit)? = null) = buildAdjust(Gamma.Builder(), options)
        /**
         * Adjusts the contrast.
         * @param level
         */
        fun contrast(options: (Contrast.Builder.() -> Unit)? = null) = buildAdjust(Contrast.Builder(), options)
        /**
         * Adjusts the image's blue channel.
         * @param level
         */
        fun blue(options: (Blue.Builder.() -> Unit)? = null) = buildAdjust(Blue.Builder(), options)
        /**
         * Adjusts the image's green channel.
         * @param level
         */
        fun green(options: (Green.Builder.() -> Unit)? = null) = buildAdjust(Green.Builder(), options)
        /**
         * Adjusts the image's red channel.
         * @param level
         */
        fun red(options: (Red.Builder.() -> Unit)? = null) = buildAdjust(Red.Builder(), options)
        /**
         * Causes all semi-transparent pixels in an image to be either fully transparent or fully opaque.
         * @param level
         */
        fun opacityThreshold(options: (OpacityThreshold.Builder.() -> Unit)? = null) =
            buildAdjust(OpacityThreshold.Builder(), options)
        /**
         * Adjusts the color saturation.
         * @param level
         */
        fun saturation(options: (Saturation.Builder.() -> Unit)? = null) = buildAdjust(Saturation.Builder(), options)
        /**
         * Applies a sharpening filter to the image.
         * @param strength
         */
        fun sharpen(options: (Sharpen.Builder.() -> Unit)? = null) = buildAdjust(Sharpen.Builder(), options)
        /**
         * Maps an input color and those similar to the input color to corresponding shades of a specified output color, taking luminosity and chroma into account, in order to recolor an object in a natural way.
         * @param
         */
        fun replaceColor(toColor: Color, options: (ReplaceColor.Builder.() -> Unit)? = null) =
            buildAdjust(ReplaceColor.Builder(toColor), options)
        /**
         * Converts the colors of every pixel in an image based on the supplied color matrix, in which the value of each color channel is calculated based on the values from all other channels (e.g. a 3x3 matrix for RGB, a 4x4 matrix for RGBA or CMYK, etc).
         * @param colorMatrix
         */
        fun recolor(colorMatrix: List<List<Float>>) = Recolor(colorMatrix)

        fun recolor(vararg colorMatrix: Float) = Recolor(colorMatrix)
        /**
         * Adjusts the fill light and blends the result with the original image.
         * @param
         */
        fun fillLight(options: (FillLight.Builder.() -> Unit)? = null) = buildAdjust(FillLight.Builder(), options)
        /**
         * Adjusts the image colors, contrast and brightness.
         * @param
         */
        fun improve(options: (Improve.Builder.() -> Unit)? = null) = buildAdjust(Improve.Builder(), options)
        /**
         * Defines the 3D lookup table to apply to images and videos.
         * @param publicID
         */
        fun by3dLut(publicId: String) = By3dLut(publicId)
    }
}

interface AdjustBuilder : TransformationComponentBuilder

private fun <T : AdjustBuilder> buildAdjust(builder: T, options: (T.() -> Unit)?): Adjust {
    options?.let { builder.it() }
    return builder.build() as Adjust
}