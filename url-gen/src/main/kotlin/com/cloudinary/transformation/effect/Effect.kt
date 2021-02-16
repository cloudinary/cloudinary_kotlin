package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.layer.source.Source

abstract class Effect : Action {

    companion object {
        /**
         * Changes the speed of the video playback.
         * @param rate
         */
        fun accelerate(options: (Accelerate.Builder.() -> Unit)? = null) = buildEffect(Accelerate.Builder(), options)
        /**
         * Removes small motion shifts from the video with a maximum extent of movement in the horizontal and vertical direction of 32 pixels.
         * @param factor
         */
        fun deshake(options: (Deshake.Builder.() -> Unit)? = null) = buildEffect(Deshake.Builder(), options)
        /**
         * Plays the video or audio file in reverse.
         * @param
         */
        fun reverse() = Reverse()
        /**
         * Causes a video clip to play forwards and then backwards.
         * @param
         */
        fun boomerang() = Boomerang()
        /**
         * Adds visual noise to the video, visible as a random flicker of "dots" or "snow".
         * @param level
         */
        fun noise(options: (Noise.Builder.() -> Unit)? = null) = buildEffect(Noise.Builder(), options)
        /**
         * Makes the background of the image transparent (or solid white for formats that do not support transparency).
         * @param level
         */
        fun makeTransparent(options: (MakeTransparent.Builder.() -> Unit)? = null) =
            buildEffect(MakeTransparent.Builder(), options)
        /**
         * Create a waveform image (in the format specified by the file extension) from the audio or video file.
         * @param options
         */
        fun waveform(options: (Waveform.Builder.() -> Unit)? = null) = buildEffect(Waveform.Builder(), options)
        /**
         * Fade in at the beginning of the video.
         * @param duration
         */
        fun fadeIn(options: (FadeIn.Builder.() -> Unit)? = null): FadeIn {
            val builder = FadeIn.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
        /**
         * Fade out at the end of the video.
         * @param duration
         */
        fun fadeOut(duration: Long) = FadeOut(duration)
        /**
         * Delivers a video or animated GIF that contains additional loops of the video/GIF.
         * @param additionalIterations
         */
        fun loop(options: (Loop.Builder.() -> Unit)? = null) = buildEffect(Loop.Builder(), options)
        /**
         * Changes the color scheme of the image to sepia.
         * @param level
         */
        fun sepia(options: (Sepia.Builder.() -> Unit)? = null) = buildEffect(Sepia.Builder(), options)
        /**
         * Converts the image to black and white.
         * @param threshold
         */
        fun blackwhite(options: (Blackwhite.Builder.() -> Unit)? = null) = buildEffect(Blackwhite.Builder(), options)
        /**
         * Applies an ordered dither filter to the image.
         * @param filter
         */
        fun dither(options: (DitherEffect.Builder.() -> Unit)? = null) = buildEffect(DitherEffect.Builder(), options)
        /**
         * Applies a vignette effect.
         * @param level
         */
        fun vignette(options: (Vignette.Builder.() -> Unit)? = null) = buildEffect(Vignette.Builder(), options)
        /**
         * Simulates the way an image would appear to someone with the specified color blind condition.
         * @param condition
         */
        fun simulateColorBlind(options: (SimulateColorBlindEffect.Builder.() -> Unit)? = null) =
            buildEffect(SimulateColorBlindEffect.Builder(), options)
        /**
         * Applies a cartoon effect to an image.
         * @param options
         */
        fun cartoonify(options: (Cartoonify.Builder.() -> Unit)? = null) = buildEffect(Cartoonify.Builder(), options)
        /**
         * Adds a shadow to the image.
         * @param strength
         */
        fun shadow(options: (Shadow.Builder.() -> Unit)? = null) = buildEffect(Shadow.Builder(), options)
        /**
         * Vectorizes the image.
         * @param
         */
        fun vectorize(options: (Vectorize.Builder.() -> Unit)? = null) = buildEffect(Vectorize.Builder(), options)
        /**
         * Adds an outline to a transparent image.
         * @param
         */
        fun outline(options: (Outline.Builder.() -> Unit)? = null) = buildEffect(Outline.Builder(), options)
        /**
         * Applies the selected artistic filter to the image.
         * @param filter
         */
        fun artisticFilter(filter: ArtisticFilter) = Artistic(filter)
        /**
         * Negates the image colors. Creates a negative of the image.
         * @param
         */
        fun negate() = Negate()
        /**
         * Removes red eyes in the image.
         */
        fun redEye() = RedEye()
        /**
         * Converts the image to gray-scale (multiple shades of gray).
         */
        fun grayscale() = Grayscale()
        /**
         * Applies an oil painting effect to the image.
         * @param strength
         */
        fun oilPaint(options: (OilPaint.Builder.() -> Unit)? = null) = buildEffect(OilPaint.Builder(), options)
        /**
         * Removes red eyes with the Advanced Facial Attribute Detection add-on.
         * @param
         */
        fun advancedRedEye() = AdvancedRedEye()
        /**
         * Applies a pixelation effect to the image.
         * @param pixelWidth
         */
        fun pixelate(options: (Pixelate.Builder.() -> Unit)? = null) = buildEffect(Pixelate.Builder(), options)
        /**
         * Applies a blurring filter to an asset.
         * @param strength
         */
        fun blur(options: (Blur.Builder.() -> Unit)? = null) = buildEffect(Blur.Builder(), options)
        /**
         * Colorizes the image.
         * @param
         */
        fun colorize(options: (Colorize.Builder.() -> Unit)? = null) = buildEffect(Colorize.Builder(), options)
        /**
         * Applies a gradient fade effect from the top edge of the image.
         * @param
         */
        fun gradientFade(options: (GradientFadeAction.Builder.() -> Unit)? = null) =
            buildEffect(GradientFadeAction.Builder(), options)
        /**
         * Applies stripes to the image to help people with common color-blind conditions to differentiate between colors that are similar for them.
         * @param
         */
        fun assistColorBlind(options: (AssistColorblind.Builder.() -> Unit)? = null) =
            buildEffect(AssistColorblind.Builder(), options)
        /**
         * Transfers the style of a source artwork to a target photograph using the Neural Artwork Style Transfer add-on.
         * @param source
         */
        fun styleTransfer(source: Source, options: (StyleTransfer.Builder.() -> Unit)? = null) =
            buildEffect(StyleTransfer.Builder(source), options)
        /**
         * Transfers the style of a source artwork to a target photograph using the Neural Artwork Style Transfer add-on.
         */
        fun styleTransfer(styleTransfer: StyleTransfer) = styleTransfer

        fun removeBackground(options: (RemoveBackground.Builder.() -> Unit)? = null): RemoveBackground {
            val builder = RemoveBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

interface EffectBuilder : TransformationComponentBuilder

private fun <T : EffectBuilder> buildEffect(builder: T, options: (T.() -> Unit)?): Effect {
    options?.let { builder.it() }
    return builder.build() as Effect
}