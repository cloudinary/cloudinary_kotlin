package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.transformation.effect.AssistColorblind.Type.STRIPES
import com.cloudinary.transformation.effect.AssistColorblind.Type.XRAY
import com.cloudinary.util.cldRanged

class GradientFade private constructor(
    private val strength: Any?,
    private val type: Any?,
    private val horizontalStartPoint: Any?,
    private val verticalStartPoint: Any?
) : Effect() {
    init {
        strength?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return asComponentString(
            "e_gradient_fade".joinWithValues(type, strength),
            horizontalStartPoint?.let { "x_$horizontalStartPoint" },
            verticalStartPoint?.let { "y_$verticalStartPoint" }
        )
    }

    class Builder : TransformationComponentBuilder {

        private var strength: Any? = null
        private var type: Any? = null
        private var horizontalStartPoint: Any? = null
        private var verticalStartPoint: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun type(type: String) = apply { this.type = type }
        fun type(type: GradientFadeType) = apply { this.type = type }
        fun horizontalStartPoint(horizontalStartPoint: Any) = apply { this.horizontalStartPoint = horizontalStartPoint }
        fun verticalStartPoint(verticalStartPoint: Any) = apply { this.verticalStartPoint = verticalStartPoint }
        fun horizontalStartPoint(horizontalStartPoint: Int) = apply { this.horizontalStartPoint = horizontalStartPoint }
        fun verticalStartPoint(verticalStartPoint: Int) = apply { this.verticalStartPoint = verticalStartPoint }
        fun horizontalStartPoint(horizontalStartPoint: Float) =
            apply { this.horizontalStartPoint = horizontalStartPoint }

        fun verticalStartPoint(verticalStartPoint: Float) = apply { this.verticalStartPoint = verticalStartPoint }

        override fun build() = GradientFade(strength, type, horizontalStartPoint, verticalStartPoint)
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

class OilPaint(private val strength: Int?) : Effect() {
    init {
        strength.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_oil_paint".joinWithValues(strength)
    }
}

class AdvancedRedEye : Effect() {
    override fun toString(): String {
        return "e_adv_redeye"
    }

}

class Pixelate internal constructor(private val squareSize: Int?, private val region: Region?) : Effect() {
    init {
        squareSize?.cldRanged(1, 200)
    }

    override fun toString(): String {
        return when (region) {
            is Region.Faces -> "e_pixelate_faces".joinWithValues(squareSize)
            is Region.OcrText -> "e_pixelate_faces".joinWithValues(squareSize) + ",g_ocr_text"
            is Custom -> "e_pixelate_region".joinWithValues(squareSize).joinWithValues(
                region.height?.let { "h_$it" },
                region.width?.let { "w_$it" },
                region.x?.let { "x_$it" },
                region.y?.let { "y_$it" },
                separator = ","
            )
            null -> "e_pixelate".joinWithValues(squareSize)
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
    private val mode: OutlineMode?,
    private val color: Color?,
    private val width: Int?,
    private val blurLevel: Int?
) : Effect() {
    init {
        width?.cldRanged(1, 100)
        blurLevel?.cldRanged(0, 200)
    }

    override fun toString(): String {
        return asComponentString(
            color?.let { "co_$color" },
            "e_outline".joinWithValues(mode, width, blurLevel)
        )
    }

    class Builder
        : TransformationComponentBuilder {

        private var mode: OutlineMode? = null
        private var color: Color? = null
        private var width: Int? = null
        private var blurLevel: Int? = null

        fun mode(mode: OutlineMode) = apply { this.mode = mode }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        fun width(width: Int) = apply { this.width = width }
        fun blurLevel(blurLevel: Int) = apply { this.blurLevel = blurLevel }


        override fun build() = Outline(mode, color, width, blurLevel)
    }
}

enum class OutlineMode(internal val value: String) {
    INNER("inner"),
    INNER_FILL("inner_fill"),
    OUTER("outer"),
    FILL("fill");

    override fun toString(): String {
        return value
    }
}

class Vectorize(
    private val numOfColors: Any?,
    private val detailsLevel: Any?,
    private val despeckleLevel: Any?,
    private val paths: Any?,
    private val cornersLevel: Any?
) : Effect() {
    override fun toString(): String {
        return "e_vectorize".joinWithValues(
            numOfColors?.let { "colors:$it" },
            detailsLevel?.let { "detail:$it" },
            despeckleLevel?.let { "despeckle:$it" },
            paths?.let { "paths:$it" },
            cornersLevel?.let { "corners:$it" }
        )
    }

    class Builder : TransformationComponentBuilder {
        private var numOfColors: Any? = null
        private var detailsLevel: Any? = null
        private var despeckleLevel: Any? = null
        private var paths: Any? = null
        private var cornersLevel: Any? = null

        fun numOfColors(numOfColors: Int) = apply { this.numOfColors = numOfColors }
        fun numOfColors(numOfColors: Any) = apply { this.numOfColors = numOfColors }
        fun numOfColors(numOfColors: Float) = apply { this.numOfColors = numOfColors }

        fun detailsLevel(detailsLevel: Int) = apply { this.detailsLevel = detailsLevel }
        fun detailsLevel(detailsLevel: Float) = apply { this.detailsLevel = detailsLevel }
        fun detailsLevel(detailsLevel: Any) = apply { this.detailsLevel = detailsLevel }

        fun despeckleLevel(despeckleLevel: Int) = apply { this.despeckleLevel = despeckleLevel }
        fun despeckleLevel(despeckleLevel: Float) = apply { this.despeckleLevel = despeckleLevel }
        fun despeckleLevel(despeckleLevel: Any) = apply { this.despeckleLevel = despeckleLevel }

        fun paths(paths: Int) = apply { this.paths = paths }
        fun paths(paths: Any) = apply { this.paths = paths }

        fun cornersLevel(cornersLevel: Int) = apply { this.cornersLevel = cornersLevel }
        fun cornersLevel(cornersLevel: Any) = apply { this.cornersLevel = cornersLevel }

        override fun build() = Vectorize(numOfColors, detailsLevel, despeckleLevel, paths, cornersLevel)
    }
}

class AssistColorblind private constructor(private val type: Type?, private val strength: Any?) : Effect() {
    override fun toString(): String {

        return "e_assist_colorblind" + when {
            strength != null -> ":$strength"
            type != null -> ":$type"
            else -> ""
        }
    }

    enum class Type(private val value: String) {
        STRIPES("stripes"),
        XRAY("xray");

        override fun toString() = value
    }

    class Builder : TransformationComponentBuilder {
        private var stripesStrength: Any? = null
        private var type: Type? = null

        override fun build() = AssistColorblind(type, stripesStrength)

        fun stripesStrength(strength: Int) = stripesStrength(strength as Any)
        fun stripesStrength(strength: Any) = apply {
            type = STRIPES
            this.stripesStrength = strength
        }

        fun xRay() = apply {
            type = XRAY
            stripesStrength = null
        }
    }
}

class Shadow internal constructor(
    private val strength: Any?,
    private val color: Color?,
    private val offsetX: Any?,
    private val offsetY: Any?
) : Effect() {

    init {
        strength.cldRanged(0, 100)
    }

    override fun toString(): String {
        return asComponentString(
            color?.let { "co_$color" },
            "e_shadow".joinWithValues(strength),
            offsetX?.let { "x_$offsetX" },
            offsetY?.let { "y_$offsetY" }
        )
    }

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null
        private var color: Color? = null
        private var offsetX: Any? = null
        private var offsetY: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun color(color: Color?) = apply { this.color = color }
        fun offsetX(offsetX: Any) = apply { this.offsetX = offsetX }
        fun offsetX(offsetX: Int) = apply { this.offsetX = offsetX }
        fun offsetY(offsetY: Any) = apply { this.offsetY = offsetY }
        fun offsetY(offsetY: Int) = apply { this.offsetY = offsetY }

        override fun build() = Shadow(strength, color, offsetX, offsetY)
    }

}

class Cartoonify(
    private val lineStrength: Any?, private val colorReductionLevel: Any?, private val blackwhite: Boolean
) : Effect() {
    override fun toString(): String {
        return "e_cartoonify".joinWithValues(lineStrength, if (blackwhite) "bw" else colorReductionLevel)
    }

    class Builder : TransformationComponentBuilder {
        private var lineStrength: Any? = null
        private var colorReductionLevel: Any? = null
        private var blackwhite: Boolean = false

        fun lineStrength(lineStrength: Int) = apply { this.lineStrength = lineStrength }

        fun lineStrength(lineStrength: Any) = apply { this.lineStrength = lineStrength }

        fun colorReduction(colorReduction: Int) = apply { this.colorReductionLevel = colorReduction }

        fun colorReduction(colorReduction: Any) = apply { this.colorReductionLevel = colorReduction }

        fun blackwhite(blackwhite: Boolean = true) = apply { this.blackwhite = blackwhite }

        override fun build() = Cartoonify(lineStrength, colorReductionLevel, blackwhite)
    }
}

class SimulateColorBlind internal constructor(private val condition: SimulateColorBlindType?) : Effect() {
    override fun toString(): String {
        return "e_simulate_colorblind".joinWithValues(condition)
    }
}

enum class SimulateColorBlindType(internal val value: String) {
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

class MakeTransparent private constructor(private val tolerance: Any?, private val color: Any?) : Effect() {
    init {
        tolerance?.cldRanged(0, 100)
        color?.let { "co_$color" }
    }

    override fun toString(): String {
        return (color?.let { "co_$it," } ?: "") + "e_make_transparent".joinWithValues(tolerance)
    }

    class Builder : TransformationComponentBuilder {
        private var tolerance: Any? = null
        private var color: Color? = null

        fun tolerance(tolerance: Int) = apply { this.tolerance = tolerance }
        fun color(color: Color) = apply { this.color = color }

        override fun build() = MakeTransparent(tolerance, color)
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

class Accelerate internal constructor(private val rate: Int?) : Effect() {
    init {
        rate?.cldRanged(-50, 100)
    }

    override fun toString(): String {
        return "e_accelerate".joinWithValues(rate)
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

class Fade internal constructor(private val duration: Long?) : Effect() {
    override fun toString(): String {
        return "e_fade".joinWithValues(duration)
    }
}

class Loop internal constructor(private val additionalIterations: Int?) : Effect() {
    override fun toString(): String {
        return "e_loop".joinWithValues(additionalIterations)
    }
}

class Blackwhite internal constructor(private val threshold: Int?) : Effect() {
    override fun toString(): String {
        return "e_blackwhite".joinWithValues(threshold)
    }
}

class DitherEffect internal constructor(private val filter: Dither?) : Effect() {
    override fun toString(): String {
        return "e_ordered_dither".joinWithValues(filter)
    }
}

enum class Dither(internal val value: Int) {
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

enum class GradientFadeType(private val value: String) {
    SYMMETRIC("symmetric"),
    SYMMETRIC_PAD("symmetric_pad");

    override fun toString() = value
}
