package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRanged

class PreviewBuilder : TransformationComponentBuilder {

    private var duration: Any? = null
    private var maximumSegments: Any? = null
    private var minimumSegmentDuration: Any? = null

    fun duration(duration: Int) = apply { this.duration = duration }
    fun maximumSegments(maximumSegments: Int) = apply { this.maximumSegments = maximumSegments }
    fun minimumSegmentDuration(minimumSegmentDuration: Int) =
        apply { this.minimumSegmentDuration = minimumSegmentDuration }

    override fun build() = effect(
        "preview",
        duration?.asNamedValue("duration", "_"),
        maximumSegments?.asNamedValue("max_seg", "_"),
        minimumSegmentDuration?.asNamedValue("min_seg_dur", "_")
    )
}


class ColorizeBuilder : TransformationComponentBuilder {
    private var level: Any? = null

    private var color: Color? = null

    fun level(level: Any) = apply { this.level = level }
    fun level(level: Int) = apply { this.level = level }
    fun color(color: Color) = apply { this.color = color }
    fun color(color: String) = apply { this.color = Color.parseString(color) }
    override fun build() = effect(
        "colorize",
        level?.cldRanged(0, 100),
        color?.cldAsColor()
    )
}

class AssistColorblindBuilder : TransformationComponentBuilder {
    private var strength: Any? = null
    private var type: Any? = null

    override fun build() = effect("assist_colorblind", type ?: strength?.run {
        AssistColorBlindType.Stripes(
            this
        )
    })

    fun strength(strength: Int) = apply { this.strength = strength }

    fun strength(strength: Any) = apply { this.strength = strength }

    fun type(type: AssistColorBlindType) = apply { this.type = type }
}

class VectorizeBuilder : TransformationComponentBuilder {
    private var colors: Any? = null
    private var detail: Any? = null
    private var despeckle: Any? = null
    private var paths: Any? = null
    private var corners: Any? = null

    override fun build() = effect(
        "vectorize",
        colors?.let { NamedValue("colors", it.cldRanged(2, 30)) },
        detail?.let { NamedValue("detail", it.cldRanged(0, 1000)) },
        despeckle?.let { NamedValue("despeckle", it.cldRanged(0, 100)) },
        paths?.let { NamedValue("paths", it.cldRanged(0, 100)) },
        corners?.let { NamedValue("corners", it.cldRanged(0, 100)) }
    )

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
}

class CartoonifyBuilder : TransformationComponentBuilder {
    private var lineStrength: Any? = null
    private var colorReduction: Any? = null
    private var blackwhite: Boolean = false

    fun lineStrength(lineStrength: Int) = apply { this.lineStrength = lineStrength }

    fun lineStrength(lineStrength: Any) = apply { this.lineStrength = lineStrength }

    fun colorReduction(colorReduction: Int) = apply { this.colorReduction = colorReduction }

    fun colorReduction(colorReduction: Any) = apply { this.colorReduction = colorReduction }

    fun blackwhite(blackwhite: Boolean) = apply { this.blackwhite = blackwhite }

    override fun build(): Effect =
        effect(
            "cartoonify",
            lineStrength,
            // blackwhite overrides color reduction
            if (blackwhite) "bw" else colorReduction
        )
}

class TrimBuilder : TransformationComponentBuilder {
    private var colorSimilarity: Any? = null
    private var colorOverride: Any? = null

    override fun build() = effect("trim", colorSimilarity, colorOverride)

    fun colorSimilarity(colorSimilarity: Int) = apply { this.colorSimilarity = colorSimilarity }

    fun colorSimilarity(colorSimilarity: Any) = apply { this.colorSimilarity = colorSimilarity }

    fun colorOverride(colorOverride: Color) = apply { this.colorOverride = colorOverride }
}

abstract class BaseRegionEffectBuilder(protected val name: String) : TransformationComponentBuilder {
    var x: Any? = null
    var y: Any? = null
    var width: Any? = null
    var height: Any? = null
    var gravity: Gravity? = null

    fun x(x: Any) = apply { this.x = x }
    fun y(y: Any) = apply { this.y = y }
    fun x(x: Int) = apply { this.x = x }
    fun y(y: Int) = apply { this.y = y }

    fun width(width: Any) = apply { this.width = width }
    fun height(height: Any) = apply { this.height = height }
    fun width(width: Int) = apply { this.width = width }
    fun height(height: Int) = apply { this.height = height }

    fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
}

class BlurRegionBuilder : BaseRegionEffectBuilder("blur_region") {
    var strength: Any? = null

    fun strength(strength: Int) = apply { this.strength = strength }

    override fun build() = effect(
        name,
        strength, // TODO range
        x?.cldAsX(),
        y?.cldAsY(),
        width?.cldAsWidth(),
        height?.cldAsHeight(),
        gravity?.cldAsGravity()
    )
}

class PixelateRegionBuilder : BaseRegionEffectBuilder("pixelate_region") {
    var squareSize: Any? = null

    fun squareSize(squareSize: Int) = apply { this.squareSize = squareSize }

    override fun build() = effect(
        name,
        squareSize, // TODO range
        x?.cldAsX(),
        y?.cldAsY(),
        width?.cldAsWidth(),
        height?.cldAsHeight(),
        gravity?.cldAsGravity()
    )
}

class ShadowBuilder : TransformationComponentBuilder {
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

    override fun build() = effect(
        "shadow",
        color?.cldAsColor(),
        strength?.cldRanged(0, 100),
        x?.cldAsX(),
        y?.cldAsY()
    )
}

class MakeTransparentBuilder : TransformationComponentBuilder {
    private var level: Int? = null
    private var color: Any? = null

    fun level(level: Int) = apply { this.level = level }
    fun color(color: Color) = apply { this.color = color }

    override fun build() = effect("make_transparent", level.cldRanged(0, 100), color?.cldAsColor())
}