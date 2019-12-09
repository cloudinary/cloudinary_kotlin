package com.cloudinary.transformation.effect

import com.cloudinary.transformation.ColorValue
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.util.cldPrepend
import com.cloudinary.util.cldRanged


class Sepia private constructor(level: Any? = null) :
    Effect("sepia", level?.cldRanged(1, 100)) {

    class Builder : TransformationComponentBuilder {
        private var level: Any? = null
        fun setLevel(level: Int?) = apply { this.level = level }
        override fun build() = Sepia(level)
    }
}

class Negate : Effect("negate")


class GrayScale : Effect("grayscale")


class BlackWhite : Effect("blackwhite")


class Colorize private constructor(
    level: Any? = null, color: ColorValue? = null
) :
    Effect("colorize", listOfNotNull(level?.cldRanged(0, 100)), listOfNotNull(color?.asParam())) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        private var color: ColorValue? = null

        override fun build(): Colorize =
            Colorize(level = level, color = color)

        fun setLevel(level: Any) = apply { this.level = level }

        fun setLevel(level: Int) = apply { this.level = level }

        fun setColor(color: ColorValue) = apply { this.color = color }
    }
}


class AssistColorBlind private constructor(
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

        override fun build(): AssistColorBlind =
            AssistColorBlind(strength = strength, type = type)

        fun setStrength(strength: Int) = apply { this.strength = strength }

        fun setStrength(strength: Any) = apply { this.strength = strength }

        fun setType(type: AssistColorBlindType) = apply { this.type = type }
    }
}

class SimulateColorblind private constructor(condition: Any? = null) :
    Effect("simulate_colorblind", condition) {
    class Builder : TransformationComponentBuilder {
        private var condition: Any? = null

        override fun build(): SimulateColorblind =
            SimulateColorblind(condition = condition)

        fun setCondition(condition: ColorBlindCondition) = apply {
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
        colors?.cldRanged(2, 30)?.cldPrepend("colors:"),
        detail?.cldRanged(0, 1000)?.cldPrepend("detail:"),
        despeckle?.cldRanged(0, 100)?.cldPrepend("despeckle:"),
        paths?.cldRanged(0, 100)?.cldPrepend("paths:"),
        corners?.cldRanged(0, 100)?.cldPrepend("corners:")
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

        fun setColors(colors: Int) = apply { this.colors = colors }

        fun setColors(colors: Any) = apply { this.colors = colors }

        fun setColors(colors: Float) = apply { this.colors = colors }

        fun setDetail(detail: Int) = apply { this.detail = detail }

        fun setDetail(detail: Float) = apply { this.detail = detail }

        fun setDetail(detail: Any) = apply { this.detail = detail }

        fun setDespeckle(despeckle: Int) = apply { this.despeckle = despeckle }

        fun setDespeckle(despeckle: Float) = apply { this.despeckle = despeckle }

        fun setDespeckle(despeckle: Any) = apply { this.despeckle = despeckle }

        fun setPaths(paths: Int) = apply { this.paths = paths }

        fun setPaths(paths: Any) = apply { this.paths = paths }

        fun setCorners(corners: Int) = apply { this.corners = corners }

        fun setCorners(corners: Any) = apply { this.corners = corners }
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

        fun setLineStrength(lineStrength: Int) = apply {
            this.lineStrength =
                lineStrength
        }

        fun setLineStrength(lineStrength: Any) = apply {
            this.lineStrength =
                lineStrength
        }

        fun setColorReduction(colorReduction: Int) = apply {
            this.colorReduction =
                colorReduction
        }

        fun setColorReduction(colorReduction: Any) = apply {
            this.colorReduction =
                colorReduction
        }

        fun setBlackwhite(blackwhite: Boolean) = apply { this.blackwhite = blackwhite }
    }
}

class Artistic private constructor(filter: Any? = null) : Effect("art", filter) {
    class Builder : TransformationComponentBuilder {
        private var filter: Any? = null

        override fun build(): Artistic =
            Artistic(filter = filter)

        fun setFilter(filter: ArtisticFilter): Builder = apply { this.filter = filter }

        fun setFilter(filter: Any): Builder = apply { this.filter = filter }
    }
}

class MakeTransparent private constructor(level: Any? = null) :
    Effect("make_transparent", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): MakeTransparent =
            MakeTransparent(level = level)

        fun setLevel(level: Any) = apply { this.level = level }

        fun setLevel(level: Int) = apply { this.level = level }
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

        fun setColorSimilarity(colorSimilarity: Int) = apply {
            this.colorSimilarity =
                colorSimilarity
        }

        fun setColorSimilarity(colorSimilarity: Any) = apply {
            this.colorSimilarity =
                colorSimilarity
        }

        fun setColorOverride(colorOverride: ColorValue) = apply {
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

        fun setLevel(level: Any) = apply { this.level = level }

        fun setLevel(level: Int) = apply { this.level = level }
    }
}

class RedEye : Effect("redeye")

class AdvRedEye : Effect("adv_redeye")

class Vignette private constructor(level: Any? = null) :
    Effect("vignette", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        override fun build(): Vignette =
            Vignette(level = level)

        fun setLevel(level: Any) = apply { this.level = level }

        fun setLevel(level: Int) = apply { this.level = level }
    }
}

class Pixelate private constructor(squareSize: Any? = null) :
    Effect("pixelate", squareSize?.cldRanged(1, 200)) {
    class Builder : TransformationComponentBuilder {
        private var squareSize: Any? = null

        override fun build(): Pixelate =
            Pixelate(squareSize = squareSize)

        fun setSquareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun setSquareSize(squareSize: Int) = apply { this.squareSize = squareSize }
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

        fun setSquareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun setSquareSize(squareSize: Int) = apply { this.squareSize = squareSize }

        fun setRegion(region: Region) = apply { this.region = region }
    }
}

class PixelateFaces private constructor(squareSize: Any? = null) :
    Effect("pixelate_faces", squareSize?.cldRanged(1, 200)) {
    class Builder : TransformationComponentBuilder {
        private var squareSize: Any? = null

        override fun build(): PixelateFaces =
            PixelateFaces(squareSize = squareSize)

        fun setSquareSize(squareSize: Any) = apply { this.squareSize = squareSize }

        fun setSquareSize(squareSize: Int) = apply { this.squareSize = squareSize }
    }
}

class Blur private constructor(strength: Any? = null) :
    Effect("blur", strength?.cldRanged(1, 2000)) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        override fun build(): Blur =
            Blur(strength = strength)

        fun setStrength(strength: Any) = apply { this.strength = strength }

        fun setStrength(strength: Int) = apply { this.strength = strength }
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

        fun setStrength(strength: Any) = apply { this.strength = strength }

        fun setStrength(strength: Int) = apply { this.strength = strength }

        fun setRegion(region: Region) = apply { this.region = region }
    }
}

class BlurFaces private constructor(strength: Any? = null) : Effect("blur_faces", strength) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        override fun build(): BlurFaces =
            BlurFaces(strength = strength)

        fun setStrength(strength: Any) = apply { this.strength = strength }

        fun setStrength(strength: Int) = apply { this.strength = strength }
    }

}

class OrderedDither private constructor(filter: Any? = null) : Effect("ordered_dither", filter) {
    class Builder : TransformationComponentBuilder {
        private var filter: Any? = null

        override fun build(): OrderedDither =
            OrderedDither(filter = filter)

        fun setFilter(filter: DitherFilter) = apply { this.filter = filter }
        fun setFilter(filter: Any) = apply { this.filter = filter }
    }
}