package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Region
import com.cloudinary.transformation.layer.source.LayerSource

abstract class Effect : Action {

    companion object {
        fun accelerate(rate: Int? = null) = Accelerate(rate)
        fun deshake(factor: Deshake.DeshakeFactor? = null) = Deshake(factor)
        fun reverse() = Reverse()
        fun boomerang() = Boomerang()
        fun noise(level: Int? = null) = Noise(level)

        fun makeTransparent(
            level: Int? = null,
            options: (MakeTransparent.Builder.() -> Unit)? = null
        ): MakeTransparent {
            val builder = MakeTransparent.Builder()
            options?.let { builder.it() }
            level?.let { builder.tolerance(it) }
            return builder.build()
        }

        fun waveform(options: (Waveform.Builder.() -> Unit)? = null): Waveform {
            val builder = Waveform.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun preview(options: (Preview.Builder.() -> Unit)? = null): Preview {
            val builder = Preview.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fadeIn(duration: Long? = null) = Fade(duration)
        fun fadeOut(duration: Long) = Fade(-duration)
        fun loop(additionalIterations: Int? = null) = Loop(additionalIterations)
        fun sepia(level: Int? = null) = Sepia(level)
        fun blackWhite(threshold: Int? = null) = Blackwhite(threshold)
        fun dither(filter: Dither? = null) = DitherEffect(filter)

        fun vignette(level: Int? = null) = Vignette(level)
        fun simulateColorBlind(condition: SimulateColorBlindType? = null) = SimulateColorBlind(condition)
        fun cartoonify(options: (Cartoonify.Builder.() -> Unit)? = null): Cartoonify {
            val builder = Cartoonify.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun shadow(strength: Int? = null, options: (Shadow.Builder.() -> Unit)? = null): Shadow {
            val builder = Shadow.Builder()
            strength?.let { builder.strength(it) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun vectorize(options: (Vectorize.Builder.() -> Unit)? = null): Vectorize {
            val builder = Vectorize.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun outline(options: (Outline.Builder.() -> Unit)? = null): Outline {
            val builder = Outline.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun trim(options: (Trim.Builder.() -> Unit)? = null): Trim {
            val builder = Trim.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun artisticFilter(filter: ArtisticFilter) = Artistic(filter)
        fun negate() = Negate()
        fun redEye() = RedEye()
        fun grayscale() = Grayscale()
        fun oilPaint(strength: Int? = null) = OilPaint(strength)
        fun advancedRedEye() = AdvancedRedEye()

        fun pixelate(pixelWidth: Int? = null, region: Region? = null) = Pixelate(pixelWidth, region)
        fun blur(strength: Int? = null, region: Region? = null) = Blur(strength, region)

        fun colorize(options: (Colorize.Builder.() -> Unit)? = null): Colorize {
            val builder = Colorize.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun gradientFade(options: (GradientFade.Builder.() -> Unit)? = null): GradientFade {
            val builder = GradientFade.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun assistColorBlind(options: (AssistColorblind.Builder.() -> Unit)? = null): AssistColorblind {
            val builder = AssistColorblind.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun styleTransfer(source: LayerSource, options: (StyleTransfer.Builder.() -> Unit)? = null): StyleTransfer {
            val builder = StyleTransfer.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun styleTransfer(styleTransfer: StyleTransfer) = styleTransfer
    }
}

