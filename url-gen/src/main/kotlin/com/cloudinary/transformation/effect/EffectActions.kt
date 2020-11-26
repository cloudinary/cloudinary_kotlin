package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRanged

class GradientFade private constructor(
    private val strength: Any?,
    private val type: Any?,
    private val x: Any?,
    private val y: Any?
) : Effect() {
    init {
        strength?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return asComponentString(
            "e_gradient_fade".joinWithValues(type, strength),
            x?.let { "x_$x" },
            y?.let { "y_$y" }
        )
    }

    enum class GradientFadeType(private val value: String) {
        SYMMETRIC("symmetric"),
        SYMMETRIC_PAD("symmetric_pad");

        override fun toString() = value
    }

    class Builder : TransformationComponentBuilder {

        private var strength: Any? = null
        private var type: Any? = null
        private var x: Any? = null
        private var y: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun type(type: String) = apply { this.type = type }
        fun type(type: GradientFadeType) = apply { this.type = type }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Any) = apply { this.y = y }
        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = GradientFade(strength, type, x, y)
    }
}

class Colorize internal constructor(private val level: Any?, private val color: Color?) : Effect() {
    init {
        level?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return (color?.let { "co_$it," } ?: "") + "e_colorize".joinWithValues(level)
    }

    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        private var color: Color? = null

        fun level(level: Any) = apply { this.level = level }
        fun level(level: Int) = apply { this.level = level }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        override fun build() = Colorize(level, color)
    }
}

class RedEye internal constructor() : Effect() {
    override fun toString(): String {
        return "e_redeye"
    }
}

class Grayscale internal constructor() : Effect() {
    override fun toString(): String {
        return "e_grayscale"
    }
}

class OilPaint(private val level: Int?) : Effect() {
    init {
        level.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_oil_paint".joinWithValues(level)
    }
}

class AdvancedRedEye : Effect() {
    override fun toString(): String {
        return "e_adv_redeye"
    }

}

class Pixelate internal constructor(private val pixelWidth: Int?, private val region: Region?) : Effect() {
    init {
        pixelWidth?.cldRanged(1, 200)
    }

    override fun toString(): String {
        return when (region) {
            is Region.Faces -> "e_pixelate_faces".joinWithValues(pixelWidth)
            is Region.OcrText -> "e_pixelate_faces".joinWithValues(pixelWidth) + ",g_ocr_text"
            is Custom -> "e_pixelate_region".joinWithValues(pixelWidth).joinWithValues(
                region.height?.let { "h_$it" },
                region.width?.let { "w_$it" },
                region.x?.let { "x_$it" },
                region.y?.let { "y_$it" },
                separator = ","
            )
            null -> "e_pixelate".joinWithValues(pixelWidth)
        }
    }
}

class Negate : Effect() {
    override fun toString(): String {
        return "e_negate"
    }
}

class Artistic internal constructor(private val filter: ArtisticFilter) : Effect() {
    override fun toString(): String {
        return "e_art".joinWithValues(filter)
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
}

class Trim private constructor(
    private val colorSimilarity: Any?,
    private val colorOverride: Any?
) : Effect() {
    override fun toString(): String {
        return "e_trim".joinWithValues(colorSimilarity, colorOverride)
    }

    class Builder : TransformationComponentBuilder {
        private var colorSimilarity: Any? = null
        private var colorOverride: Any? = null


        fun colorSimilarity(colorSimilarity: Int) = apply { this.colorSimilarity = colorSimilarity }

        fun colorSimilarity(colorSimilarity: Any) = apply { this.colorSimilarity = colorSimilarity }

        fun colorOverride(colorOverride: Color) = apply { this.colorOverride = colorOverride }

        override fun build() = Trim(colorSimilarity, colorOverride)
    }
}

class Outline private constructor(
    private val mode: Mode?,
    private val color: Color?,
    private val width: Int?,
    private val blur: Int?
) : Effect() {
    init {
        width?.cldRanged(1, 100)
        blur?.cldRanged(0, 200)
    }

    override fun toString(): String {
        return asComponentString(
            color?.let { "co_$color" },
            "e_outline".joinWithValues(mode, width, blur)
        )
    }

    enum class Mode(internal val value: String) {
        INNER("inner"),
        INNER_FILL("inner_fill"),
        OUTER("outer"),
        FILL("fill");

        override fun toString(): String {
            return value
        }
    }

    class Builder
        : TransformationComponentBuilder {

        private var mode: Mode? = null
        private var color: Color? = null
        private var width: Int? = null
        private var blur: Int? = null

        fun mode(mode: Mode) = apply { this.mode = mode }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        fun width(width: Int) = apply { this.width = width }
        fun blur(blur: Int) = apply { this.blur = blur }


        override fun build() = Outline(mode, color, width, blur)
    }
}

class Vectorize(
    private val colors: Any?,
    private val detail: Any?,
    private val despeckle: Any?,
    private val paths: Any?,
    private val corners: Any?
) : Effect() {
    override fun toString(): String {
        return "e_vectorize".joinWithValues(
            colors?.let { "colors:$it" },
            detail?.let { "detail:$it" },
            despeckle?.let { "despeckle:$it" },
            paths?.let { "paths:$it" },
            corners?.let { "corners:$it" }
        )
    }

    class Builder : TransformationComponentBuilder {
        private var colors: Any? = null
        private var detail: Any? = null
        private var despeckle: Any? = null
        private var paths: Any? = null
        private var corners: Any? = null

        fun colors(colors: Int) = apply { this.colors = colors }
        fun colors(colors: Any) = apply { this.colors = colors }
        fun colors(colors: Float) = apply { this.colors = colors }

        fun detail(detail: Int) = apply { this.detail = detail }
        fun detail(detail: Float) = apply { this.detail = detail }
        fun detail(detail: Any) = apply { this.detail = detail }

        fun despeckle(despeckle: Int) = apply { this.despeckle = despeckle }
        fun despeckle(despeckle: Float) = apply { this.despeckle = despeckle }
        fun despeckle(despeckle: Any) = apply { this.despeckle = despeckle }

        fun paths(paths: Int) = apply { this.paths = paths }
        fun paths(paths: Any) = apply { this.paths = paths }

        fun corners(corners: Int) = apply { this.corners = corners }
        fun corners(corners: Any) = apply { this.corners = corners }

        override fun build() = Vectorize(colors, detail, despeckle, paths, corners)
    }
}

class AssistColorblind private constructor(private val type: Type?, private val strength: Any?) : Effect() {
    override fun toString(): String {
        return "e_assist_colorblind".joinWithValues(type, strength)
    }

    enum class Type(private val value: String) {
        STRIPES("stripes"),
        XRAY("xray");

        override fun toString() = value
    }

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null
        private var type: Type? = null

        override fun build() = AssistColorblind(type, strength)

        fun stripes(strength: Int) = stripes(strength as Any)
        fun stripes(strength: Any) = apply {
            type = null
            this.strength = strength
        }

        fun xRay() = apply {
            type = Type.XRAY
            strength = null
        }
    }
}

class Shadow internal constructor(
    private val strength: Any?,
    private val color: Color?,
    private val x: Any?,
    private val y: Any?
) : Effect() {

    init {
        strength.cldRanged(0, 100)
    }

    override fun toString(): String {
        return asComponentString(
            color?.let { "co_$color" },
            "e_shadow".joinWithValues(strength),
            x?.let { "x_$x" },
            y?.let { "y_$y" }
        )
    }

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null
        private var color: Color? = null
        private var x: Any? = null
        private var y: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun color(color: Color?) = apply { this.color = color }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Any) = apply { this.y = y }
        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }

        override fun build() = Shadow(strength, color, x, y)
    }

}

class Cartoonify(
    private val lineStrength: Any?, private val colorReduction: Any?, private val blackwhite: Boolean
) : Effect() {
    override fun toString(): String {
        return "e_cartoonify".joinWithValues(lineStrength, if (blackwhite) "bw" else colorReduction)
    }

    class Builder : TransformationComponentBuilder {
        private var lineStrength: Any? = null
        private var colorReduction: Any? = null
        private var blackwhite: Boolean = false

        fun lineStrength(lineStrength: Int) = apply { this.lineStrength = lineStrength }

        fun lineStrength(lineStrength: Any) = apply { this.lineStrength = lineStrength }

        fun colorReduction(colorReduction: Int) = apply { this.colorReduction = colorReduction }

        fun colorReduction(colorReduction: Any) = apply { this.colorReduction = colorReduction }

        fun blackwhite(blackwhite: Boolean = true) = apply { this.blackwhite = blackwhite }

        override fun build() = Cartoonify(lineStrength, colorReduction, blackwhite)
    }
}

class SimulateColorBlind internal constructor(private val condition: Type?) : Effect() {
    enum class Type(internal val value: String) {
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

    override fun toString(): String {
        return "e_simulate_colorblind".joinWithValues(condition)
    }
}

class MakeTransparent private constructor(private val level: Int?, private val color: Any?) : Effect() {
    init {
        level.cldRanged(0, 100)
        color?.let { "co_$color" }
    }

    override fun toString(): String {
        return (color?.let { "co_$it," } ?: "") + "e_make_transparent".joinWithValues(level)
    }

    class Builder : TransformationComponentBuilder {
        private var level: Int? = null
        private var color: Color? = null

        fun level(level: Int) = apply { this.level = level }
        fun color(color: Color) = apply { this.color = color }

        override fun build() = MakeTransparent(level, color)
    }
}

class Waveform private constructor(private var color: Any?, private val backgroundColor: Color?) : Effect() {
    override fun toString(): String {
        return asComponentString(
            backgroundColor?.let { "b_$backgroundColor" },
            color?.let { "co_$color" },
            Flag.waveform()
        )
    }

    class Builder : TransformationComponentBuilder {
        private var color: Color? = null
        private var backgroundColor: Color? = null

        fun color(color: Color) = apply { this.color = color }
        fun background(color: Color) = apply { this.backgroundColor = color }

        override fun build() = Waveform(color, backgroundColor)
    }
}

class Accelerate internal constructor(private val percent: Int?) : Effect() {
    init {
        percent?.cldRanged(-50, 100)
    }

    override fun toString(): String {
        return "e_accelerate".joinWithValues(percent)
    }
}
/*
{
    type: Accelerate
    level: 3
}
 */

class Deshake internal constructor(private val factor: DeshakeFactor?) : Effect() {
    override fun toString(): String {
        return "e_deshake".joinWithValues(factor)
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
}

class Reverse internal constructor() : Effect() {
    override fun toString(): String {
        return "e_reverse"
    }
}

class Boomerang internal constructor() : Effect() {
    override fun toString(): String {
        return "e_boomerang"
    }
}

class Noise internal constructor(private val level: Int?) : Effect() {
    override fun toString(): String {
        return "e_noise".joinWithValues(level)
    }
}

class Vignette internal constructor(private val level: Int?) : Effect() {
    init {
        level.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_vignette".joinWithValues(level)
    }
}

class Blur internal constructor(private val strength: Int?, private val region: Region?) : Effect() {
    init {
        strength?.cldRanged(1, 2000)
    }

    override fun toString(): String {
        return when (region) {
            is Region.Faces -> "e_blur_faces".joinWithValues(strength)
            is Region.OcrText -> "e_blur_faces".joinWithValues(strength) + ",g_ocr_text"
            is Custom -> "e_blur_region".joinWithValues(strength).joinWithValues(
                region.height?.let { "h_$it" },
                region.width?.let { "w_$it" },
                region.x?.let { "x_$it" },
                region.y?.let { "y_$it" },
                separator = ","
            )
            null -> "e_blur".joinWithValues(strength)
        }
    }
}

class Sepia internal constructor(private val level: Int?) : Effect() {
    init {
        level?.cldRanged(1, 100)
    }

    override fun toString(): String {
        return "e_sepia".joinWithValues(level)
    }
}

class Preview private constructor(
    private val duration: Int?,
    private val maximumSegments: Int?,
    private val minimumSegmentDuration: Int?
) : Effect() {
    override fun toString(): String {
        return "e_preview".joinWithValues(
            duration?.let { "duration_$it" },
            maximumSegments?.let { "max_seg_$it" },
            minimumSegmentDuration?.let { "min_seg_dur_$it" }
        )
    }

    class Builder : TransformationComponentBuilder {

        private var duration: Int? = null
        private var maximumSegments: Int? = null
        private var minimumSegmentDuration: Int? = null

        fun duration(duration: Int) = apply { this.duration = duration }
        fun maximumSegments(maximumSegments: Int) = apply { this.maximumSegments = maximumSegments }
        fun minimumSegmentDuration(minimumSegmentDuration: Int) =
            apply { this.minimumSegmentDuration = minimumSegmentDuration }

        override fun build() = Preview(duration, maximumSegments, minimumSegmentDuration)
    }
}

class Fade internal constructor(private val milliseconds: Long?) : Effect() {
    override fun toString(): String {
        return "e_fade".joinWithValues(milliseconds)
    }
}

class Loop internal constructor(private val loops: Int?) : Effect() {
    override fun toString(): String {
        return "e_loop".joinWithValues(loops)
    }
}

class Blackwhite internal constructor(private val balance: Int?) : Effect() {
    override fun toString(): String {
        return "e_blackwhite".joinWithValues(balance)
    }
}

class OrderedDither internal constructor(private val filter: DitherFilter?) : Effect() {
    override fun toString(): String {
        return "e_ordered_dither".joinWithValues(filter)
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
}