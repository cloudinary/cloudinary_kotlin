package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Region
import com.cloudinary.transformation.effect.SimulateColorBlind.Type
import com.cloudinary.transformation.layer.source.LayerSource

abstract class Effect : Action {

    companion object {
        fun accelerate(percent: Int? = null) = Accelerate(percent)
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
            level?.let { builder.level(it) }
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

        fun fadeIn(milliseconds: Long? = null) = Fade(milliseconds)
        fun fadeOut(milliseconds: Long) = Fade(-milliseconds)
        fun loop(loops: Int? = null) = Loop(loops)
        fun sepia(level: Int? = null) = Sepia(level)
        fun blackWhite(balance: Int? = null) = Blackwhite(balance)
        fun orderedDither(filter: OrderedDither.DitherFilter? = null) = OrderedDither(filter)

        fun vignette(level: Int? = null) = Vignette(level)
        fun simulateColorBlind(condition: Type? = null) = SimulateColorBlind(condition)
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

        fun artisticFilter(filter: Artistic.ArtisticFilter) = Artistic(filter)
        fun negate() = Negate()
        fun redEye() = RedEye()
        fun grayscale() = Grayscale()
        fun oilPaint(level: Int? = null) = OilPaint(level)
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

