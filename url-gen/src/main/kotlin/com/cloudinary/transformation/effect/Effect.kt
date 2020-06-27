package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamsAction
import com.cloudinary.transformation.cldAsEffect
import com.cloudinary.transformation.layer.MediaSource
import com.cloudinary.util.cldPositiveNumber
import com.cloudinary.util.cldRanged

class Effect(private val action: Action) : Action by action {

    companion object {

        fun makeTransparent(level: Int? = null, options: (MakeTransparentBuilder.() -> Unit)? = null): Effect {
            val builder = MakeTransparentBuilder()
            options?.let { builder.it() }
            level?.let { builder.level(it) }
            return builder.build()
        }

        fun waveform(options: (WaveformBuilder.() -> Unit)? = null): Effect {
            val builder = WaveformBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun accelerate(percent: Int? = null) = effect("accelerate", percent?.cldRanged(-50, 100))
        fun deshake(factor: DeshakeFactor? = null) = effect("deshake", factor)
        fun noise(level: Int? = null) = effect("noise", level?.cldRanged(0, 100))
        fun boomerang() = effect("boomerang")
        fun reverse() = effect("reverse")
        fun preview(options: (PreviewBuilder.() -> Unit)? = null): Effect {
            val builder = PreviewBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fadeIn(milliseconds: Long? = null) = fade(milliseconds?.cldPositiveNumber())
        fun fadeOut(milliseconds: Long) = fade(-milliseconds.cldPositiveNumber())
        private fun fade(milliseconds: Long? = null) = effect("fade", milliseconds)

        fun loop(loops: Int? = null) = effect("loop", loops)

        fun sepia(level: Int? = null) = effect("sepia", level?.cldRanged(1, 100))

        fun simulateColorBlind(condition: SimulateColorBlind? = null) = effect("simulate_colorblind", condition)

        fun cartoonify(options: (CartoonifyBuilder.() -> Unit)? = null): Effect {
            val builder = CartoonifyBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun shadow(strength: Int? = null, options: (ShadowBuilder.() -> Unit)? = null): Effect {
            val builder = ShadowBuilder()
            strength?.let { builder.strength(it) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun orderedDither(filter: DitherFilter? = null) = effect("ordered_dither", filter)

        fun blurRegion(options: (BlurRegionBuilder.() -> Unit)? = null): Effect {
            val builder = BlurRegionBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun vectorize(options: (VectorizeBuilder.() -> Unit)? = null): Effect {
            val builder = VectorizeBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun blackWhite() = effect("blackwhite")

        fun blur(strength: Int? = null) = effect("blur", strength?.cldRanged(1, 2000))

        fun outline(width: Int? = null, blur: Int? = null, options: (OutlineBuilder.() -> Unit)? = null): Effect {
            val builder = OutlineBuilder()
            width?.let { builder.width(it) }
            blur?.let { builder.blur(it) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun vignette(level: Int? = null) = effect("vignette", level.cldRanged(0, 100))

        fun trim(options: (TrimBuilder.() -> Unit)? = null): Effect {
            val builder = TrimBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun artisticFilter(filter: ArtisticFilter) = effect("art", filter)

        fun negate() = effect("negate")

        fun colorize(options: (ColorizeBuilder.() -> Unit)? = null): Effect {
            val builder = ColorizeBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun redEye() = effect("redeye")

        fun pixelateRegion(options: (PixelateRegionBuilder.() -> Unit)? = null): Effect {
            val builder = PixelateRegionBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun assistColorBlind(options: (AssistColorblindBuilder.() -> Unit)? = null): Effect {
            val builder = AssistColorblindBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun pixelateFaces(squareSize: Int? = null) = effect("pixelate_faces", squareSize?.cldRanged(1, 200))

        fun grayscale() = effect("grayscale")

        fun gradientFade(strength: Int? = null, options: (GradientFadeBuilder.() -> Unit)? = null): Effect {
            val builder = GradientFadeBuilder()
            strength?.let { builder.strength(strength) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun oilPaint(level: Int? = null) = effect("oil_paint", level.cldRanged(0, 100))

        fun advancedRedEye() = effect("adv_redeye")

        fun pixelate(squareSize: Int? = null) = effect("pixelate", squareSize?.cldRanged(1, 200))

        fun blurFaces(strength: Any? = null) = effect("blur_faces", strength)

        fun styleTransfer(source: MediaSource, options: (StyleTransferBuilder.() -> Unit)? = null): Effect {
            val builder = StyleTransferBuilder(source)
            options?.let { builder.it() }
            return Effect(builder.build())
        }
    }
}

enum class DitherFilter(internal val value: Int) {
    THRESHOLD_1X1_NON_DITHER(0),
    CHECKERBOARD_2X1_DITHER(1),
    ORDERED_2X2_DISPERSED(2),
    ORDERED_3X3_DISPERSED(3),
    ORDERED_4X4_DISPERSED(4),
    ORDERED_8X8_DISPERSED(5),
    HALFTONE_4X4_ANGLED(6),
    HALFTONE_6X6_ANGLED(7),
    HALFTONE_8X8_ANGLED(8),
    HALFTONE_4X4_ORTHOGONAL(9),
    HALFTONE_6X6_ORTHOGONAL(10),
    HALFTONE_8X8_ORTHOGONAL(11),
    HALFTONE_16X16_ORTHOGONAL(12),
    CIRCLES_5X5_BLACK(13),
    CIRCLES_5X5_WHITE(14),
    CIRCLES_6X6_BLACK(15),
    CIRCLES_6X6_WHITE(16),
    CIRCLES_7X7_BLACK(17),
    CIRCLES_7X7_WHITE(18);

    override fun toString(): String {
        return value.toString()
    }
}

enum class Improve(internal val value: String) {
    OUTDOOR("outdoor"),
    INDOOR("indoor");

    override fun toString(): String {
        return value
    }
}

enum class SimulateColorBlind(internal val value: String) {
    DEUTERANOPIA("deuteranopia"),
    PROTANOPIA("protanopia"),
    TRITANOPIA("tritanopia"),
    TRITANOMALY("tritanomaly"),
    DEUTERANOMALY("deuteranomaly"),
    CONEMONOCHROMACY("cone_monochromacy"),
    RODMONOCHROMACY("rod_monochromacy"), ;

    override fun toString(): String {
        return value
    }
}

enum class ArtisticFilter(private val value: String) {
    AL_DENTE("al_dente"),
    ATHENA("athena"),
    AUDREY("audrey"),
    AURORA("aurora"),
    DAGUERRE("daguerre"),
    EUCALYPTUS("eucalyptus"),
    FES("fes"),
    FROST("frost"),
    HAIRSPRAY("hairspray"),
    HOKUSAI("hokusai"),
    INCOGNITO("incognito"),
    LINEN("linen"),
    PEACOCK("peacock"),
    PRIMAVERA("primavera"),
    QUARTZ("quartz"),
    RED_ROCK("red_rock"),
    REFRESH("refresh"),
    SIZZLE("sizzle"),
    SONNET("sonnet"),
    UKULELE("ukulele"),
    ZORRO("zorro");

    override fun toString(): String {
        return value
    }
}

enum class DeshakeFactor(private val factor: Int) {
    FACTOR16(16),
    FACTOR32(32),
    FACTOR48(48),
    FACTOR64(64);

    override fun toString(): String {
        return factor.toString()
    }
}

internal fun effect(name: String, vararg values: Any?) = Effect(effectAction(name, *values))

internal fun effectAction(
    name: String,
    vararg values: Any?
): ParamsAction {
    val (params, paramValues) = values.filterNotNull().partition { i -> i is Param }

    // This list was generated using partition by type, however the compiler does not detect it. 100% safe cast.
    @Suppress("UNCHECKED_CAST") val list = listOf((listOf(name) + paramValues).cldAsEffect()) + (params as List<Param>)
    return ParamsAction(list)
}

enum class Outline(internal val value: String) {
    INNER("inner"),
    INNER_FILL("inner_fill"),
    OUTER("outer"),
    FILL("fill"), ;

    override fun toString(): String {
        return value
    }
}