package com.cloudinary.transformation.effect

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRanged


class Sepia private constructor(level: Any? = null) :
    Effect("sepia", level?.cldRanged(1, 100)) {

    class Builder : TransformationComponentBuilder {
        private var level: Any? = null
        fun level(level: Int?) = apply { this.level = level }
        override fun build() = Sepia(level)
    }
}

class Negate : Effect("negate")


class Grayscale : Effect("grayscale")


class BlackWhite : Effect("blackwhite")


class Colorize private constructor(
    level: Any? = null, color: Color? = null
) :
    Effect("colorize", listOfNotNull(level?.cldRanged(0, 100)), listOfNotNull(color?.asParam())) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        private var color: Color? = null

        override fun build(): Colorize =
            Colorize(level = level, color = color)

        fun level(level: Any) = apply { this.level = level }
        fun level(level: Int) = apply { this.level = level }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
    }
}


class AssistColorblind private constructor(
    strength: Any? = null, type: Any? = null
) :
    Effect("assist_colorblind", type ?: strength?.run {
        AssistColorBlindType.Stripes(
            this
        )
    }) {

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        private var type: Any? = null

        override fun build(): AssistColorblind =
            AssistColorblind(strength = strength, type = type)

        fun strength(strength: Int) = apply { this.strength = strength }

        fun strength(strength: Any) = apply { this.strength = strength }

        fun type(type: AssistColorBlindType) = apply { this.type = type }
    }
}

class SimulateColorblind private constructor(condition: Any? = null) :
    Effect("simulate_colorblind", condition) {
    class Builder : TransformationComponentBuilder {
        private var condition: Any? = null

        override fun build(): SimulateColorblind =
            SimulateColorblind(condition = condition)

        fun condition(condition: ColorBlindCondition) = apply {
            this.condition = condition
        }
    }
}

class Vectorize private constructor(
    colors: Any? = null,
    detail: Any? = null,
    despeckle: Any? = null,
    paths: Any? = null,
    corners: Any? = null
) : Effect(
    "vectorize", listOfNotNull(
        colors?.let { NamedValue("colors", colors.cldRanged(2, 30)) },
        detail?.let { NamedValue("detail", detail.cldRanged(0, 1000)) },
        despeckle?.let { NamedValue("despeckle", despeckle.cldRanged(0, 100)) },
        paths?.let { NamedValue("paths", paths.cldRanged(0, 100)) },
        corners?.let { NamedValue("corners", corners.cldRanged(0, 100)) }
    )
) {
    class Builder : TransformationComponentBuilder {
        private var colors: Any? = null

        private var detail: Any? = null

        private var despeckle: Any? = null

        private var paths: Any? = null

        private var corners: Any? = null

        override fun build(): Vectorize =
            Vectorize(
                colors = colors, detail = detail, despeckle = despeckle, paths
                = paths, corners = corners
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
}

class Cartoonify private constructor(
    lineStrength: Any? = null,
    colorReduction: Any? = null,
    blackwhite: Boolean? = null
) : Effect(
    "cartoonify",
    listOfNotNull(
        lineStrength,
        // blackwhite overrides color reduction
        if (blackwhite == true) "bw" else colorReduction
    )
) {
    class Builder : TransformationComponentBuilder {
        private var lineStrength: Any? = null

        private var colorReduction: Any? = null

        private var blackwhite: Boolean = false

        override fun build(): Cartoonify =
            Cartoonify(
                lineStrength = lineStrength, colorReduction = colorReduction,
                blackwhite = blackwhite
            )

        fun lineStrength(lineStrength: Int) = apply {
            this.lineStrength =
                lineStrength
        }

        fun lineStrength(lineStrength: Any) = apply {
            this.lineStrength =
                lineStrength
        }

        fun colorReduction(colorReduction: Int) = apply {
            this.colorReduction =
                colorReduction
        }

        fun colorReduction(colorReduction: Any) = apply {
            this.colorReduction =
                colorReduction
        }

        fun blackwhite(blackwhite: Boolean) = apply { this.blackwhite = blackwhite }
    }
}

class Artistic private constructor(filter: Any? = null) : Effect("art", filter) {
    class Builder : TransformationComponentBuilder {
        private var filter: Any? = null

        override fun build(): Artistic =
            Artistic(filter = filter)

        fun filter(filter: ArtisticFilter): Builder = apply { this.filter = filter }

        fun filter(filter: Any): Builder = apply { this.filter = filter }
    }
}

class MakeTransparent private constructor(level: Any? = null) :
    Effect("make_transparent", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): MakeTransparent =
            MakeTransparent(level = level)

        fun level(level: Any) = apply { this.level = level }

        fun level(level: Int) = apply { this.level = level }
    }
}

class Trim private constructor(
    colorSimilarity: Any? = null, colorOverride: Any? = null
) :
    Effect("trim", listOfNotNull(colorSimilarity, colorOverride)) {
    class Builder : TransformationComponentBuilder {
        private var colorSimilarity: Any? = null
        private var colorOverride: Any? = null

        override fun build(): Trim =
            Trim(
                colorSimilarity = colorSimilarity,
                colorOverride = colorOverride
            )

        fun colorSimilarity(colorSimilarity: Int) = apply {
            this.colorSimilarity =
                colorSimilarity
        }

        fun colorSimilarity(colorSimilarity: Any) = apply {
            this.colorSimilarity =
                colorSimilarity
        }

        fun colorOverride(colorOverride: Color) = apply {
            this.colorOverride =
                colorOverride
        }
    }

}

class OilPaint private constructor(level: Any? = null) :
    Effect("oil_paint", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): OilPaint =
            OilPaint(level = level)

        fun level(level: Any) = apply { this.level = level }

        fun level(level: Int) = apply { this.level = level }
    }
}

class Redeye : Effect("redeye")

class AdvRedeye : Effect("adv_redeye")

class Vignette private constructor(level: Any? = null) :
    Effect("vignette", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): Vignette =
            Vignette(level = level)

        fun level(level: Any) = apply { this.level = level }

        fun level(level: Int) = apply { this.level = level }
    }
}

class Pixelate private constructor(squareSize: Any? = null) :
    Effect("pixelate", squareSize?.cldRanged(1, 200)) {
    class Builder : TransformationComponentBuilder {
        private var squareSize: Any? = null

        override fun build(): Pixelate =
            Pixelate(squareSize = squareSize)

        fun squareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun squareSize(squareSize: Int) = apply { this.squareSize = squareSize }
    }
}

class PixelateRegion private constructor(
    squareSize: Any? = null, region: Region? = null
) : Effect(
    "pixelate_region", listOfNotNull(squareSize?.cldRanged(1, 200)), region?.list
        ?: emptyList()
) {
    class Builder : TransformationComponentBuilder {
        private var squareSize: Any? = null

        private var region: Region? = null

        override fun build(): PixelateRegion =
            PixelateRegion(
                squareSize = squareSize,
                region = region
            )

        fun squareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun squareSize(squareSize: Int) = apply { this.squareSize = squareSize }

        fun region(region: Region) = apply { this.region = region }
    }
}

class PixelateFaces private constructor(squareSize: Any? = null) :
    Effect("pixelate_faces", squareSize?.cldRanged(1, 200)) {
    class Builder : TransformationComponentBuilder {
        private var squareSize: Any? = null

        override fun build(): PixelateFaces =
            PixelateFaces(squareSize = squareSize)

        fun squareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun squareSize(squareSize: Int) = apply { this.squareSize = squareSize }
    }
}

class Blur private constructor(strength: Any? = null) :
    Effect("blur", strength?.cldRanged(1, 2000)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): Blur =
            Blur(strength = level)

        fun level(level: Any) = apply { this.level = level }

        fun level(level: Int) = apply { this.level = level }
    }
}

class BlurRegion private constructor(
    strength: Any? = null, region: Region? = null
) : Effect(
    "blur_region", listOfNotNull(strength), region?.list
        ?: emptyList()
) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        private var region: Region? = null

        override fun build(): BlurRegion =
            BlurRegion(strength = strength, region = region)

        fun strength(strength: Any) = apply { this.strength = strength }

        fun strength(strength: Int) = apply { this.strength = strength }

        fun region(region: Region) = apply { this.region = region }
    }
}

class Shadow private constructor(
    strength: Any? = null,
    color: Color? = null,
    x: Any? = null,
    y: Any? = null
) : Effect(
    "shadow",
    listOfNotNull(strength?.cldRanged(0, 100)),
    listOfNotNull(color?.asParam(), x?.cldAsX(), y?.cldAsY())
) {
    override fun create(params: Map<String, Param>) = Shadow(params)

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

class BlurFaces private constructor(strength: Any? = null) : Effect("blur_faces", strength) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        override fun build(): BlurFaces =
            BlurFaces(strength = strength)

        fun strength(strength: Any) = apply { this.strength = strength }

        fun strength(strength: Int) = apply { this.strength = strength }
    }
}

class OrderedDither private constructor(filter: Any? = null) : Effect("ordered_dither", filter) {
    class Builder : TransformationComponentBuilder {
        private var filter: Any? = null

        override fun build(): OrderedDither =
            OrderedDither(filter = filter)

        fun filter(filter: DitherFilter) = apply { this.filter = filter }
        fun filter(filter: Any) = apply { this.filter = filter }
    }
}


