package com.cloudinary.transformation

class Border(
    private val width: Any,
    private val color: Any,
    private val roundCorners: Any? = null
) : Action {

    private val type = "solid"

    companion object {
        fun solid(width: Any, color: Any, border: (Builder.() -> Unit)? = null): Border {
            val builder = Builder()
            builder.color(color)
            builder.width(width)
            border?.let { builder.it() }
            return builder.build()
        }

        fun solid(width: Int, color: Color, border: (Builder.() -> Unit)? = null) = solid(width as Any, color, border)

        fun solid(border: (Builder.() -> Unit)? = null): Border {
            val builder = Builder()
            border?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return "bo_${width}px_${type}_$color".joinWithValues(roundCorners, separator = ",")
    }

    class Builder : TransformationComponentBuilder {
        private var width: Any? = null
        private var color: Any? = null
        private var roundCorners: Any? = null

        fun width(width: Int) = apply { this.width = width }
        fun width(width: Any) = apply { this.width = width }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        fun color(color: Color) = apply { this.color = color }
        fun color(color: Any) = apply { this.color = color }

        // TODO: Corner Radius inside Border action is a temp suggestions:
        fun roundCorners(roundCorners: RoundCorners) = apply { this.roundCorners = roundCorners }

        override fun build(): Border {
            val width = width
            val color = color
            require(width != null && color != null) { "Width and Color qualifiers are required" }
            return Border(width, color, roundCorners)
        }
    }
}

