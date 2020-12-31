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

enum class GradientDirection(private val value: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    DIAGONAL_DESC("diagonal_desc"),
    DIAGONAL_ASC("diagonal_asc");

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

    @TransformationDsl
    class Builder {
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        fun build(): BorderBackground {
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

    @TransformationDsl
    class Builder {
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

        fun build(): BorderGradientBackground {
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
    class Builder {
        private var contrast: Boolean? = null
        private var palette: List<Color>? = null

        fun contrast() = apply { this.contrast = true }
        fun palette(colors: List<Color>) = apply { this.palette = colors }
        fun palette(vararg colors: Color) = apply { this.palette = colors.toList() }

        fun build(): PredominantBackground {
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

    @TransformationDsl
    class Builder {
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

        fun build(): PredominantGradientBackground {
            return PredominantGradientBackground(contrast, palette, gradientColors, gradientDirection)
        }
    }
}