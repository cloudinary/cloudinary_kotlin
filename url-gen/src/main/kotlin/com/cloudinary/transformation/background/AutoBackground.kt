package com.cloudinary.transformation.background

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

class AutoBackground(
    private val type: String? = null,
    private val contrast: Boolean = false,
    private val gradient: Gradient? = null,
    private val palette: List<Color>? = null
) : Background() {
    override fun getValues(): String {
        if (type.isNullOrBlank()) return "auto"

        val fullType = type.joinWithValues(
            if (gradient != null) "gradient" else if (contrast) "contrast" else null,
            separator = "_"
        )
        return "auto".joinWithValues(
            fullType,
            gradient?.toString()?.let { if (it.isNotBlank()) it else null },
            if (palette.isNullOrEmpty()) null else "palette".joinWithValues(*palette.toTypedArray(), separator = "_")
        )
    }

    @TransformationDsl
    class Builder(private val type: String? = null) {
        private var contrast: Boolean = false
        private var gradient: Gradient? = null
        private var palette: List<Color>? = null

        fun palette(vararg colors: Color) = apply {
            this.palette = colors.toList()
        }

        fun contrast(contrast: Boolean = true) = apply { this.contrast = contrast }

        fun gradient(options: (Gradient.Builder.() -> Unit)? = null) = apply {
            val builder = Gradient.Builder()
            options?.let { builder.it() }
            this.gradient = builder.build()
        }

        fun build() = AutoBackground(type, contrast, gradient, palette)
    }

    class Gradient internal constructor(
        private val colors: Int? = null,
        private val direction: GradientDirection? = null
    ) {
        override fun toString() = listOfNotNull(colors, direction).joinToString(":")

        class Builder {
            private var colors: Int? = null
            private var direction: GradientDirection? = null

            fun direction(direction: GradientDirection) = apply { this.direction = direction }
            fun colors(colors: Int) = apply { this.colors = colors }

            fun build() = Gradient(colors, direction)
        }
    }

    enum class GradientDirection(private val value: String) {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical"),
        DIAGONAL_DESC("diagonal_desc"),
        DIAGONAL_ASC("diagonal_asc");

        override fun toString() = value
    }
}

