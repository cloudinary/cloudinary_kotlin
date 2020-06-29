package com.cloudinary.transformation.background

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRanged

class BlurredBackgroundBuilder : TransformationComponentBuilder {
    private var intensity: Any? = null
    private var brightness: Any? = null

    fun intensity(intensity: Int) = apply { this.intensity = intensity }
    fun brightness(brightness: Int) = apply { this.brightness = brightness }

    override fun build(): PadBackground {
        val value = ParamValue(listOfNotNull("blur", intensity?.cldRanged(1, 2000), brightness?.cldRanged(-300, 100)))
        return PadBackground(value.cldAsBackground().asAction())
    }
}

class AutoBackgroundBuilder(private val type: String? = null) {
    private var contrast: Boolean = false
    private var gradient: Gradient? = null

    fun contrast(contrast: Boolean = true) = apply { this.contrast = contrast }
    fun gradient(options: (GradientBuilder.() -> Unit)? = null) = apply {
        val builder = GradientBuilder()
        options?.let { builder.it() }
        this.gradient = builder.build()
    }

    fun build(): PadBackground {
        val fullType = ParamValue(
            listOfNotNull(
                type,
                if (gradient != null) "gradient" else null,
                if (contrast) "contrast" else null
            ),
            "_"
        )

        val gradientStr = gradient?.toString()
        val gradientValue = if (gradientStr.isNullOrBlank()) null else gradientStr
        val paramValue = ParamValue(
            listOfNotNull(
                "auto",
                fullType,
                gradientValue
            )
        )

        return PadBackground(paramValue.cldAsBackground().asAction())
    }
}

// horizontal, vertical, diagonal_desc, and diagonal_asc. Default: horizontal
// TODO enum should be in Adjust file, not in the adjuserBuilders file.
enum class GradientDirection(private val value: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    DIAGONAL_DESC("diagonal_desc"),
    DIAGONAL_ASC("diagonal_asc");

    override fun toString() = value
}

class GradientBuilder {
    private var palette: ParamValue? = null
    private var colors: Int? = null
    private var direction: GradientDirection? = null

    fun palette(vararg colors: Color) = apply {
        this.palette = ParamValue(listOf("palette") + colors, separator = "_")
    }

    fun direction(direction: GradientDirection) = apply { this.direction = direction }
    fun colors(colors: Int) = apply { this.colors = colors }

    fun build() = Gradient(
        listOfNotNull(
            colors,
            direction,
            palette
        )
    )
}

class Gradient internal constructor(values: List<Any>) : ParamValue(values)