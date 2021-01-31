package com.cloudinary.transformation.background

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

// TODO: Extract base class as all of these are basically the same

class AutoBackground : Background() {
    override fun getValues(): String {
        return "auto"
    }
}

class GradientDirection private constructor(private val value: String) {
    companion object {
        private val horizontal = GradientDirection("horizontal")
        fun horizontal() = horizontal
        private val vertical = GradientDirection("vertical")
        fun vertical() = vertical
        private val diagonal_desc = GradientDirection("diagonal_desc")
        fun diagonalDesc() = diagonal_desc
        private val diagonal_asc = GradientDirection("diagonal_asc")
        fun diagonalAsc() = diagonal_asc
    }

    override fun toString() = value
}

class BorderBackground(private val contrast: Boolean? = null, private val palette: List<Color>? = null) : Background() {
    init {
        require(palette == null || palette.isNotEmpty()) { "If using a palette it must contain at least one color" }
    }

    override fun getValues(): String {
        return "auto:border".joinWithValues(if (contrast == true) "contrast" else null, separator = "_")
            .joinWithValues(palette?.let { "palette_" + it.joinToString(separator = "_") })
    }

    class Builder : BackgroundBuilder<BorderBackground> {
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        override fun build(): BorderBackground {
            return BorderBackground(contrast, palette)
        }
    }
}

class BorderGradientBackground(
    private val contrast: Boolean? = null,
    private val palette: List<Color>? = null,
    private val gradientColors: Int? = null,
    private val gradientDirection: GradientDirection? = null
) :
    Background() {
    init {
        require(palette == null || palette.isNotEmpty()) { "If using a palette it must contain at least one color" }
    }

    override fun getValues(): String {
        val fullType =
            "auto:border_gradient".joinWithValues(if (contrast == true) "contrast" else null, separator = "_")

        return fullType
            .joinWithValues(
                gradientColors,
                gradientDirection,
                palette?.let { "palette_" + it.joinToString(separator = "_") })
    }

    class Builder : BackgroundBuilder<BorderGradientBackground> {
        private var gradientColors: Int? = null
        private var gradientDirection: GradientDirection? = null
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun gradientColors(gradientColors: Int) = apply { this.gradientColors = gradientColors }
        fun gradientDirection(gradientDirection: GradientDirection) =
            apply { this.gradientDirection = gradientDirection }

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        override fun build(): BorderGradientBackground {
            return BorderGradientBackground(contrast, palette, gradientColors, gradientDirection)
        }
    }
}

class PredominantBackground(private val contrast: Boolean? = null, private val palette: List<Color>? = null) :
    Background() {
    init {
        require(palette == null || palette.isNotEmpty()) { "If using a palette it must contain at least one color" }
    }

    override fun getValues(): String {
        return "auto:predominant".joinWithValues(if (contrast == true) "contrast" else null, separator = "_")
            .joinWithValues(palette?.let { "palette_" + it.joinToString(separator = "_") })
    }

    @TransformationDsl
    class Builder : BackgroundBuilder<PredominantBackground> {
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        override fun build(): PredominantBackground {
            return PredominantBackground(contrast, palette)
        }
    }
}

class PredominantGradientBackground(
    private val contrast: Boolean? = null,
    private val palette: List<Color>? = null,
    private val gradientColors: Int? = null,
    private val gradientDirection: GradientDirection? = null
) :
    Background() {
    init {
        require(palette == null || palette.isNotEmpty()) { "If using a palette it must contain at least one color" }
    }

    override fun getValues(): String {
        val fullType =
            "auto:predominant_gradient".joinWithValues(if (contrast == true) "contrast" else null, separator = "_")

        return fullType
            .joinWithValues(
                gradientColors,
                gradientDirection,
                palette?.let { "palette_" + it.joinToString(separator = "_") })
    }

    class Builder : BackgroundBuilder<PredominantGradientBackground> {
        private var gradientColors: Int? = null
        private var gradientDirection: GradientDirection? = null
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun gradientColors(gradientColors: Int) = apply { this.gradientColors = gradientColors }
        fun gradientDirection(gradientDirection: GradientDirection) =
            apply { this.gradientDirection = gradientDirection }

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        override fun build(): PredominantGradientBackground {
            return PredominantGradientBackground(contrast, palette, gradientColors, gradientDirection)
        }
    }
}