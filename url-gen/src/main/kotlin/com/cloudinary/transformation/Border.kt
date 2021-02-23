package com.cloudinary.transformation

class Border(
    private val width: Any,
    private val color: Any,
    private val roundCorners: Any? = null
) : Action {

    private val type = "solid"

    companion object {
        fun solid(width: Any, color: Any, border: (Builder.() -> Unit)? = null): Border {
            val builder = Builder(width, color)
            border?.let { builder.it() }
            return builder.build()
        }

        fun solid(width: Int, color: Color, border: (Builder.() -> Unit)? = null) = solid(width as Any, color, border)
    }

    override fun toString(): String {
        return "bo_${width}px_${type}_$color".joinWithValues(roundCorners, separator = ",")
    }

    class Builder(private val width: Any, private val color: Any) : TransformationComponentBuilder {
        constructor(width: Int, color: Color) : this(width as Any, color as Any)

        private var roundCorners: Any? = null


        fun roundCorners(roundCorners: RoundCorners) = apply { this.roundCorners = roundCorners }

        override fun build(): Border {
            val width = width
            val color = color
            return Border(width, color, roundCorners)
        }
    }
}

