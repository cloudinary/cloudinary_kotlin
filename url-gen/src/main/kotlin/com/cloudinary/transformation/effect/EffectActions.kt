package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.transformation.effect.AssistColorblind.Type.STRIPES
import com.cloudinary.transformation.effect.AssistColorblind.Type.XRAY
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.util.cldPositiveNumber
import com.cloudinary.util.cldRanged

class GradientFadeAction private constructor(
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

    class Builder : EffectBuilder {

        private var strength: Any? = null
        private var type: Any? = null
        private var horizontalStartPoint: Any? = null
        private var verticalStartPoint: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun type(type: String) = apply { this.type = type }
        fun type(type: GradientFade) = apply { this.type = type }
        fun horizontalStartPoint(horizontalStartPoint: Any) = apply { this.horizontalStartPoint = horizontalStartPoint }
        fun verticalStartPoint(verticalStartPoint: Any) = apply { this.verticalStartPoint = verticalStartPoint }
        fun horizontalStartPoint(horizontalStartPoint: Int) = apply { this.horizontalStartPoint = horizontalStartPoint }
        fun verticalStartPoint(verticalStartPoint: Int) = apply { this.verticalStartPoint = verticalStartPoint }
        fun horizontalStartPoint(horizontalStartPoint: Float) =
            apply { this.horizontalStartPoint = horizontalStartPoint }

        fun verticalStartPoint(verticalStartPoint: Float) = apply { this.verticalStartPoint = verticalStartPoint }

        override fun build() = GradientFadeAction(strength, type, horizontalStartPoint, verticalStartPoint)
    }
}

class Theme internal constructor(private val color: Color, private val photosensitivity: Int? = null): Effect() {

    init {
        photosensitivity?.cldRanged(0, 200)
    }

    override fun toString(): String {
        return "e_theme".joinWithValues(color?.let { "color_$it" }).joinWithValues(photosensitivity?.let { "photosensitivity_$it" })
    }

    inner class Builder: EffectBuilder {
        private var photosensitivity: Int? = null

        fun photosensitivity(photosensitivity: Int) = apply { this.photosensitivity = photosensitivity }

        override fun build() = Theme(color, photosensitivity)
    }
}

class Colorize internal constructor(private val level: Any?, private val color: Color?) : Effect() {
    init {
        level?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return (color?.let { "co_$it," } ?: "") + "e_colorize".joinWithValues(level)
    }

    class Builder : EffectBuilder {
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

class OilPaint(private val strength: Any?) : Effect() {
    init {
        strength.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_oil_paint".joinWithValues(strength)
    }

    class Builder : EffectBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        override fun build() = OilPaint(strength)
    }
}

class AdvancedRedEye : Effect() {
    override fun toString(): String {
        return "e_adv_redeye"
    }

}

class Pixelate internal constructor(private val squareSize: Any?, private val region: Region?) : Effect() {
    init {
        squareSize?.cldRanged(1, 200)
    }

    override fun toString(): String {
        return when (region) {
            is Region.faces -> "e_pixelate_faces".joinWithValues(squareSize)
            is Region.ocr -> "e_pixelate_region".joinWithValues(squareSize) + ",g_ocr_text"
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

    class Builder : EffectBuilder {
        private var squareSize: Any? = null
        private var region: Region? = null

        fun squareSize(squareSize: Int) = apply { this.squareSize = squareSize }
        fun squareSize(squareSize: Expression) = apply { this.squareSize = squareSize }
        fun squareSize(squareSize: String) = apply { this.squareSize = squareSize }

        fun region(region: Region) = apply { this.region = region }

        override fun build(): Pixelate {
            return Pixelate(squareSize, region)
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

class ArtisticFilter(private val value: String) {
    companion object {
        private val alDente = ArtisticFilter("al_dente")
        fun alDente() = alDente
        private val athena = ArtisticFilter("athena")
        fun athena() = athena
        private val audrey = ArtisticFilter("audrey")
        fun audrey() = audrey
        private val aurora = ArtisticFilter("aurora")
        fun aurora() = aurora
        private val daguerre = ArtisticFilter("daguerre")
        fun daguerre() = daguerre
        private val eucalyptus = ArtisticFilter("eucalyptus")
        fun eucalyptus() = eucalyptus
        private val fes = ArtisticFilter("fes")
        fun fes() = fes
        private val frost = ArtisticFilter("frost")
        fun frost() = frost
        private val hairspray = ArtisticFilter("hairspray")
        fun hairspray() = hairspray
        private val hokusai = ArtisticFilter("hokusai")
        fun hokusai() = hokusai
        private val incognito = ArtisticFilter("incognito")
        fun incognito() = incognito
        private val linen = ArtisticFilter("linen")
        fun linen() = linen
        private val peacock = ArtisticFilter("peacock")
        fun peacock() = peacock
        private val primavera = ArtisticFilter("primavera")
        fun primavera() = primavera
        private val quartz = ArtisticFilter("quartz")
        fun quartz() = quartz
        private val redRock = ArtisticFilter("red_rock")
        fun redRock() = redRock
        private val refresh = ArtisticFilter("refresh")
        fun refresh() = refresh
        private val sizzle = ArtisticFilter("sizzle")
        fun sizzle() = sizzle
        private val sonnet = ArtisticFilter("sonnet")
        fun sonnet() = sonnet
        private val ukulele = ArtisticFilter("ukulele")
        fun ukulele() = ukulele
        private val zorro = ArtisticFilter("zorro")
        fun zorro() = zorro
    }

    override fun toString(): String {
        return value
    }
}

class Outline private constructor(
    private val mode: Any?,
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
        : EffectBuilder {

        private var mode: Any? = null
        private var color: Color? = null
        private var width: Int? = null
        private var blurLevel: Int? = null

        fun mode(mode: OutlineMode) = apply { this.mode = mode }
        fun mode(mode: String) = apply { this.mode = mode }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        fun width(width: Int) = apply { this.width = width }
        fun blurLevel(blurLevel: Int) = apply { this.blurLevel = blurLevel }


        override fun build() = Outline(mode, color, width, blurLevel)
    }
}

class OutlineMode(internal val value: String) {
    companion object {

        private val inner = OutlineMode("inner")
        fun inner() = inner
        private val innerFill = OutlineMode("inner_fill")
        fun innerFill() = innerFill
        private val outer = OutlineMode("outer")
        fun outer() = outer
        private val fill = OutlineMode("fill")
        fun fill() = fill
    }

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

    class Builder : EffectBuilder {
        private var numOfColors: Any? = null
        private var detailsLevel: Any? = null
        private var despeckleLevel: Any? = null
        private var paths: Any? = null
        private var cornersLevel: Any? = null

        fun numOfColors(numOfColors: Int) = apply { this.numOfColors = numOfColors }
        fun numOfColors(numOfColors: Any) = apply { this.numOfColors = numOfColors }

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

    internal enum class Type(private val value: String) {
        STRIPES("stripes"),
        XRAY("xray");

        override fun toString() = value
    }

    class Builder : EffectBuilder {
        private var stripesStrength: Any? = null
        private var type: Type? = null

        override fun build() = AssistColorblind(type, stripesStrength)

        fun stripesStrength(strength: Int) = stripesStrength(strength as Any)
        fun stripesStrength(strength: Any) = apply {
            type = STRIPES
            this.stripesStrength = strength
        }

        fun xray() = apply {
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

    class Builder : EffectBuilder {
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

    class Builder : EffectBuilder {
        private var lineStrength: Any? = null
        private var colorReductionLevel: Any? = null
        private var blackwhite: Boolean = false

        fun lineStrength(lineStrength: Int) = apply { this.lineStrength = lineStrength }

        fun lineStrength(lineStrength: Any) = apply { this.lineStrength = lineStrength }

        fun colorReductionLevel(colorReductionLevel: Int) = apply { this.colorReductionLevel = colorReductionLevel }

        fun colorReductionLevel(colorReductionLevel: Any) = apply { this.colorReductionLevel = colorReductionLevel }

        fun blackwhite(blackwhite: Boolean = true) = apply { this.blackwhite = blackwhite }

        override fun build() = Cartoonify(lineStrength, colorReductionLevel, blackwhite)
    }
}

class SimulateColorBlindEffect internal constructor(private val condition: SimulateColorBlind?) : Effect() {
    override fun toString(): String {
        return "e_simulate_colorblind".joinWithValues(condition)
    }

    class Builder : EffectBuilder {
        private var condition: SimulateColorBlind? = null

        fun condition(condition: SimulateColorBlind?) = apply { this.condition = condition }

        override fun build() = SimulateColorBlindEffect(condition)
    }
}

class SimulateColorBlind private constructor(internal val value: String) {
    companion object {
        private val deuteranopia = SimulateColorBlind("deuteranopia")
        fun deuteranopia() = deuteranopia
        private val protanopia = SimulateColorBlind("protanopia")
        fun protanopia() = protanopia
        private val tritanopia = SimulateColorBlind("tritanopia")
        fun tritanopia() = tritanopia
        private val tritanomaly = SimulateColorBlind("tritanomaly")
        fun tritanomaly() = tritanomaly
        private val deuteranomaly = SimulateColorBlind("deuteranomaly")
        fun deuteranomaly() = deuteranomaly
        private val conemonochromacy = SimulateColorBlind("cone_monochromacy")
        fun conemonochromacy() = conemonochromacy
        private val rodmonochromacy = SimulateColorBlind("rod_monochromacy")
        fun rodmonochromacy() = rodmonochromacy
    }

    override fun toString(): String {
        return value
    }
}

class MakeTransparent private constructor(private val tolerance: Any?, private val colorToReplace: Any?) : Effect() {
    init {
        tolerance?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return (colorToReplace?.let { "co_$it," } ?: "") + "e_make_transparent".joinWithValues(tolerance)
    }

    class Builder : EffectBuilder {
        private var tolerance: Any? = null
        private var colorToReplace: Color? = null

        fun tolerance(tolerance: Int) = apply { this.tolerance = tolerance }
        fun colorToReplace(color: Color) = apply { this.colorToReplace = color }

        override fun build() = MakeTransparent(tolerance, colorToReplace)
    }
}

class Waveform internal constructor(private val format: Format, private var color: Any? = null, private val backgroundColor: Color? = null) : Effect() {

    override fun toString(): String {
        return asComponentString(
            "f_$format",
            backgroundColor?.let { "b_$backgroundColor" },
            color?.let { "co_$color" },
            Flag.waveform()
        )
    }

    inner class Builder : EffectBuilder {
        private var color: Color? = null
        private var backgroundColor: Color? = null

        fun color(color: Color) = apply { this.color = color }
        fun background(color: Color) = apply { this.backgroundColor = color }

        override fun build() = Waveform(format, color, backgroundColor)
    }
}

class Accelerate internal constructor(private val rate: Any?) : Effect() {
    init {
        rate?.cldRanged(-50, 100)
    }

    override fun toString(): String {
        return "e_accelerate".joinWithValues(rate)
    }

    class Builder : EffectBuilder {
        private var rate: Any? = null

        fun rate(rate: Int) = apply { this.rate = rate }
        fun rate(rate: Expression) = apply { this.rate = rate }
        fun rate(rate: String) = apply { this.rate = rate }

        override fun build() = Accelerate(rate)
    }
}

class Deshake internal constructor(private val factor: ShakeStrength?) : Effect() {
    override fun toString(): String {
        return "e_deshake".joinWithValues(factor)
    }


    class Builder : EffectBuilder {
        private var shakeStrength: ShakeStrength? = null

        fun shakeStrength(shakeStrength: ShakeStrength) = apply { this.shakeStrength = shakeStrength }

        override fun build() = Deshake(shakeStrength)
    }
}

class ShakeStrength private constructor(private val factor: Int) {
    companion object {
        fun pixels16() = ShakeStrength(16)
        fun pixels32() = ShakeStrength(32)
        fun pixels48() = ShakeStrength(48)
        fun pixels64() = ShakeStrength(64)
    }

    override fun toString(): String {
        return factor.toString()
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

class Noise internal constructor(private val level: Any?) : Effect() {
    override fun toString(): String {
        return "e_noise".joinWithValues(level)
    }

    class Builder : LevelEffectBuilder() {
        override fun build() = Noise(level)
    }
}

class Vignette internal constructor(private val strength: Any?) : Effect() {
    init {
        strength.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_vignette".joinWithValues(strength)
    }

    class Builder : EffectBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        override fun build(): Vignette {
            return Vignette(strength)
        }
    }
}

class Blur internal constructor(private val strength: Any?, private val region: Region?) : Effect() {
    init {
        strength?.cldRanged(1, 2000)
    }

    class Builder : EffectBuilder {
        private var strength: Any? = null
        private var region: Region? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        fun region(region: Region) = apply { this.region = region }

        override fun build(): Blur {
            return Blur(strength, region)
        }
    }

    override fun toString(): String {
        return when (region) {
            is Region.faces -> "e_blur_faces".joinWithValues(strength)
            is Region.ocr -> "e_blur_region".joinWithValues(strength) + ",g_ocr_text"
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

class Sepia internal constructor(private val level: Any?) : Effect() {
    init {
        level?.cldRanged(1, 100)
    }

    override fun toString(): String {
        return "e_sepia".joinWithValues(level)
    }

    class Builder : LevelEffectBuilder() {
        override fun build() = Sepia(level)
    }
}

class Fade internal constructor(private val duration: Long?) : Effect() {
    override fun toString(): String {
        return "e_fade".joinWithValues(duration)
    }
}

class FadeIn internal constructor(private val duration: Long?) : Effect() {
    init {
        duration?.cldPositiveNumber()
    }

    override fun toString(): String {
        return "e_fade".joinWithValues(duration)
    }

    class Builder : TransformationComponentBuilder {
        private var duration: Long? = null

        fun duration(duration: Long) = apply { this.duration = duration }
        override fun build() = FadeIn(duration)

    }
}

class FadeOut internal constructor(private val duration: Long) : Effect() {
    init {
        duration.cldPositiveNumber()
    }

    override fun toString(): String {
        return "e_fade:-$duration"
    }
}

class Loop internal constructor(private val additionalIterations: Int?) : Effect() {
    override fun toString(): String {
        return "e_loop".joinWithValues(additionalIterations)
    }

    class Builder : EffectBuilder {
        private var additionalIterations: Int? = null

        fun additionalIterations(additionalIterations: Int) = apply { this.additionalIterations = additionalIterations }


        override fun build() = Loop(additionalIterations)
    }
}

class Blackwhite internal constructor(private val threshold: Any?) : Effect() {
    override fun toString(): String {
        return "e_blackwhite".joinWithValues(threshold)
    }

    class Builder : EffectBuilder {
        private var threshold: Any? = null

        fun threshold(threshold: Int) = apply { this.threshold = threshold }
        fun threshold(threshold: Expression) = apply { this.threshold = threshold }
        fun threshold(threshold: String) = apply { this.threshold = threshold }

        override fun build() = Blackwhite(threshold)
    }
}

class RemoveBackground(private val screen: Boolean? = null, private val colorToRemove: Color? = null) : Effect() {
    override fun toString(): String {
        return "e_bgremoval".joinWithValues(screen?.let { "screen" }, colorToRemove?.toString(false))
    }

    class Builder : TransformationComponentBuilder {
        private var screen: Boolean? = null
        private var colorToRemove: Color? = null

        fun screen(screen: Boolean = true) = apply { this.screen = screen }
        fun colorToRemove(colorToRemove: Color) = apply { this.colorToRemove = colorToRemove }

        override fun build() = RemoveBackground(screen, colorToRemove)
    }
}

class DitherEffect internal constructor(private val filter: Dither?) : Effect() {
    override fun toString(): String {
        return "e_ordered_dither".joinWithValues(filter)
    }

    class Builder : EffectBuilder {
        private var dither: Dither? = null

        fun type(dither: Dither) = apply { this.dither = dither }

        override fun build() = DitherEffect(dither)
    }
}

class Dither private constructor(internal val value: Int) {
    companion object {
        fun threshold1x1NonDither() = Dither(0)
        fun checkerboard2x1Dither() = Dither(1)
        fun ordered2x2Dispersed() = Dither(2)
        fun ordered3x3Dispersed() = Dither(3)
        fun ordered4x4Dispersed() = Dither(4)
        fun ordered8x8Dispersed() = Dither(5)
        fun halftone4x4Angled() = Dither(6)
        fun halftone6x6Angled() = Dither(7)
        fun halftone8x8Angled() = Dither(8)
        fun halftone4x4Orthogonal() = Dither(9)
        fun halftone6x6Orthogonal() = Dither(10)
        fun halftone8x8Orthogonal() = Dither(11)
        fun halftone16x16Orthogonal() = Dither(12)
        fun circles5x5Black() = Dither(13)
        fun circles5x5White() = Dither(14)
        fun circles6x6Black() = Dither(15)
        fun circles6x6White() = Dither(16)
        fun circles7x7Black() = Dither(17)
        fun circles7x7White() = Dither(18)
    }

    override fun toString(): String {
        return value.toString()
    }
}

class GradientFade(private val value: String) {
    companion object {
        private val symmetric = GradientFade("symmetric")
        fun symmetric() = symmetric
        private val symmetricPad = GradientFade("symmetric_pad")
        fun symmetricPad() = symmetricPad
    }

    override fun toString() = value
}

abstract class LevelEffectBuilder : EffectBuilder {
    protected var level: Any? = null

    fun level(level: Int) = apply { this.level = level }
    fun level(level: Expression) = apply { this.level = level }
    fun level(level: String) = apply { this.level = level }
}