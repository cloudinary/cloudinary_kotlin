package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.layer.source.Source

abstract class Effect : Action {

    companion object {
        fun accelerate(options: (Accelerate.Builder.() -> Unit)? = null) = buildEffect(Accelerate.Builder(), options)
        fun deshake(options: (Deshake.Builder.() -> Unit)? = null) = buildEffect(Deshake.Builder(), options)
        fun reverse() = Reverse()
        fun boomerang() = Boomerang()

        fun noise(options: (Noise.Builder.() -> Unit)? = null) = buildEffect(Noise.Builder(), options)

        fun makeTransparent(options: (MakeTransparent.Builder.() -> Unit)? = null) =
            buildEffect(MakeTransparent.Builder(), options)

        @Deprecated("This function will be removed in the next major version, use VideoEdit.waveform instead", replaceWith = ReplaceWith("VideoEdit.waveform(format)"))
        fun waveform(format: Format, options: (Waveform.Builder.() -> Unit)? = null) : Effect {
            if (options == null) {
                return Waveform(format)
            }
            return buildEffect(Waveform(format).Builder(), options)
        }

        fun fadeIn(options: (FadeIn.Builder.() -> Unit)? = null): FadeIn {
            val builder = FadeIn.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fadeOut(duration: Long) = FadeOut(duration)
        fun loop(options: (Loop.Builder.() -> Unit)? = null) = buildEffect(Loop.Builder(), options)
        fun sepia(options: (Sepia.Builder.() -> Unit)? = null) = buildEffect(Sepia.Builder(), options)
        fun blackwhite(options: (Blackwhite.Builder.() -> Unit)? = null) = buildEffect(Blackwhite.Builder(), options)
        fun dither(options: (DitherEffect.Builder.() -> Unit)? = null) = buildEffect(DitherEffect.Builder(), options)
        fun vignette(options: (Vignette.Builder.() -> Unit)? = null) = buildEffect(Vignette.Builder(), options)

        fun simulateColorBlind(options: (SimulateColorBlindEffect.Builder.() -> Unit)? = null) =
            buildEffect(SimulateColorBlindEffect.Builder(), options)

        fun cartoonify(options: (Cartoonify.Builder.() -> Unit)? = null) = buildEffect(Cartoonify.Builder(), options)
        fun shadow(options: (Shadow.Builder.() -> Unit)? = null) = buildEffect(Shadow.Builder(), options)

        fun vectorize(options: (Vectorize.Builder.() -> Unit)? = null) = buildEffect(Vectorize.Builder(), options)
        fun outline(options: (Outline.Builder.() -> Unit)? = null) = buildEffect(Outline.Builder(), options)
        fun artisticFilter(filter: ArtisticFilter) = Artistic(filter)
        fun negate() = Negate()
        fun redEye() = RedEye()
        fun grayscale() = Grayscale()

        fun oilPaint(options: (OilPaint.Builder.() -> Unit)? = null) = buildEffect(OilPaint.Builder(), options)
        fun advancedRedEye() = AdvancedRedEye()
        fun pixelate(options: (Pixelate.Builder.() -> Unit)? = null) = buildEffect(Pixelate.Builder(), options)
        fun blur(options: (Blur.Builder.() -> Unit)? = null) = buildEffect(Blur.Builder(), options)
        fun colorize(options: (Colorize.Builder.() -> Unit)? = null) = buildEffect(Colorize.Builder(), options)
        fun gradientFade(options: (GradientFadeAction.Builder.() -> Unit)? = null) =
            buildEffect(GradientFadeAction.Builder(), options)

        fun assistColorBlind(options: (AssistColorblind.Builder.() -> Unit)? = null) =
            buildEffect(AssistColorblind.Builder(), options)

        fun styleTransfer(source: Source, options: (StyleTransfer.Builder.() -> Unit)? = null) =
            buildEffect(StyleTransfer.Builder(source), options)

        fun styleTransfer(styleTransfer: StyleTransfer) = styleTransfer

        fun removeBackground(options: (RemoveBackground.Builder.() -> Unit)? = null): RemoveBackground {
            val builder = RemoveBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
        fun theme(color: Color, options: (Theme.Builder.() -> Unit)? = null): Effect {
            if (options == null) {
                return Theme(color)
            }
            return buildEffect(Theme(color).Builder(), options)
        }
    }
}

interface EffectBuilder : TransformationComponentBuilder

fun <T : EffectBuilder> buildEffect(builder: T, options: (T.() -> Unit)?): Effect {
    options?.let { builder.it() }
    return builder.build() as Effect
}
