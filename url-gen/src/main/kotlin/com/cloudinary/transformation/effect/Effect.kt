package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.util.cldToString

open class Effect(params: Map<String, Param>) : Action<Effect>(params) {
    constructor(params: Collection<Param>) : this(params.cldToActionMap())
    constructor (
        name: String,
        values: List<Any> = emptyList(),
        params: Collection<Param> = emptyList()
    ) : this(listOf(effectParam(name, values)) + params)

    constructor (name: String, value: Any?) : this(name, listOfNotNull(value))

    override fun create(params: Map<String, Param>) =
        Effect(params)

    companion object {
        fun simulateColorblind(
            simulateColorblind: (SimulateColorblind.Builder.() -> Unit)?
            = null
        ): SimulateColorblind {
            val builder = SimulateColorblind.Builder()
            simulateColorblind?.let { builder.it() }
            return builder.build()
        }

        fun cartoonify(cartoonify: (Cartoonify.Builder.() -> Unit)? = null): Cartoonify {
            val builder = Cartoonify.Builder()
            cartoonify?.let { builder.it() }
            return builder.build()
        }

        fun orderedDither(orderedDither: (OrderedDither.Builder.() -> Unit)? = null):
                OrderedDither {
            val builder = OrderedDither.Builder()
            orderedDither?.let { builder.it() }
            return builder.build()
        }

        fun blurRegion(blurRegion: (BlurRegion.Builder.() -> Unit)? = null): BlurRegion {
            val builder = BlurRegion.Builder()
            blurRegion?.let { builder.it() }
            return builder.build()
        }

        fun vectorize(vectorize: (Vectorize.Builder.() -> Unit)? = null): Vectorize {
            val builder = Vectorize.Builder()
            vectorize?.let { builder.it() }
            return builder.build()
        }

        fun blackWhite(): BlackWhite = BlackWhite()

        fun blur(blur: (Blur.Builder.() -> Unit)? = null): Blur {
            val builder = Blur.Builder()
            blur?.let { builder.it() }
            return builder.build()
        }

        fun sepia(sepia: (Sepia.Builder.() -> Unit)? = null): Sepia {
            val builder = Sepia.Builder()
            sepia?.let { builder.it() }
            return builder.build()
        }

        fun vignette(vignette: (Vignette.Builder.() -> Unit)? = null): Vignette {
            val builder = Vignette.Builder()
            vignette?.let { builder.it() }
            return builder.build()
        }

        fun trim(trim: (Trim.Builder.() -> Unit)? = null): Trim {
            val builder = Trim.Builder()
            trim?.let { builder.it() }
            return builder.build()
        }

        fun artistic(artistic: (Artistic.Builder.() -> Unit)? = null): Artistic {
            val builder = Artistic.Builder()
            artistic?.let { builder.it() }
            return builder.build()
        }

        fun negate(): Negate = Negate()

        fun colorize(colorize: (Colorize.Builder.() -> Unit)? = null): Colorize {
            val builder = Colorize.Builder()
            colorize?.let { builder.it() }
            return builder.build()
        }

        fun redEye(): RedEye = RedEye()

        fun pixelateRegion(pixelateRegion: (PixelateRegion.Builder.() -> Unit)? = null):
                PixelateRegion {
            val builder = PixelateRegion.Builder()
            pixelateRegion?.let { builder.it() }
            return builder.build()
        }

        fun assistColorBlind(
            assistColorBlind: (AssistColorBlind.Builder.() -> Unit)? =
                null
        ): AssistColorBlind {
            val builder = AssistColorBlind.Builder()
            assistColorBlind?.let { builder.it() }
            return builder.build()
        }

        fun pixelateFaces(pixelateFaces: (PixelateFaces.Builder.() -> Unit)? = null):
                PixelateFaces {
            val builder = PixelateFaces.Builder()
            pixelateFaces?.let { builder.it() }
            return builder.build()
        }

        fun grayScale(): GrayScale = GrayScale()

        fun oilPaint(oilPaint: (OilPaint.Builder.() -> Unit)? = null): OilPaint {
            val builder = OilPaint.Builder()
            oilPaint?.let { builder.it() }
            return builder.build()
        }

        fun advRedEye(): AdvRedEye = AdvRedEye()

        fun pixelate(pixelate: (Pixelate.Builder.() -> Unit)? = null): Pixelate {
            val builder = Pixelate.Builder()
            pixelate?.let { builder.it() }
            return builder.build()
        }

        fun blurFaces(blurFaces: (BlurFaces.Builder.() -> Unit)? = null): BlurFaces {
            val builder = BlurFaces.Builder()
            blurFaces?.let { builder.it() }
            return builder.build()
        }

        fun makeTransparent(makeTransparent: (MakeTransparent.Builder.() -> Unit)? = null):
                MakeTransparent {
            val builder = MakeTransparent.Builder()
            makeTransparent?.let { builder.it() }
            return builder.build()
        }
    }
}

internal fun effectParam(
    name: String,
    values: List<Any>
) = Param(
    "effect",
    "e",
    ParamValue(listOf(name) + values)
)

class Region(x: Int? = null, y: Int? = null, width: Int? = null, height: Int? = null) {
    internal val list = listOfNotNull(x?.cldAsX(), y?.cldAsY(), width?.cldAsWidth(), height?.cldAsHeight())
}

sealed class AssistColorBlindType(value: Any) : ParamValue(listOf(value)) {
    class Stripes(strength: Any) : AssistColorBlindType(strength.cldToString()) {
        constructor(strength: Int) : this(strength as Any)
    }

    class XRay : AssistColorBlindType("xray")
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
        return value.cldToString()
    }
}

enum class ImproveMode(internal val value: String) {
    OUTDOOR("outdoor"),
    INDOOR("indoor");

    override fun toString(): String {
        return value
    }
}

enum class ColorBlindCondition(internal val value: String) {
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