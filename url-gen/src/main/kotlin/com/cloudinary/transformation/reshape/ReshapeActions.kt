package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldRanged
import com.cloudinary.util.validateAllNotNull

class Shear private constructor(
    private val skewX: Any? = null,
    private val skewY: Any? = null
) : Reshape() {
    override fun toString(): String {
        return "e_shear".joinWithValues(skewX, skewY)
    }

    class Builder : TransformationComponentBuilder {
        private var skewX: Any? = null
        private var skewY: Any? = null

        fun skewX(skewX: Int) = apply { this.skewX = skewX }
        fun skewY(skewY: Int) = apply { this.skewY = skewY }

        override fun build() = Shear(skewX, skewY)
    }
}

class DistortArc(private val degrees: Int) : Reshape() {
    init {
        degrees.cldRanged(-360, 360)
    }

    override fun toString(): String {
        return "e_distort".joinWithValues("arc", degrees)
    }
}

class Distort private constructor(
    private val topLeftX: Any,
    private val topLeftY: Any,
    private val topRightX: Any,
    private val topRightY: Any,
    private val bottomRightX: Any,
    private val bottomRightY: Any,
    private val bottomLeftX: Any,
    private val bottomLeftY: Any
) : Reshape() {

    override fun toString(): String {
        return "e_distort".joinWithValues(
            topLeftX,
            topLeftY,
            topRightX,
            topRightY,
            bottomRightX,
            bottomRightY,
            bottomLeftX,
            bottomLeftY
        )
    }

    class Builder : TransformationComponentBuilder {
        private var topLeftX: Any? = null
        private var topLeftY: Any? = null
        private var topRightX: Any? = null
        private var topRightY: Any? = null
        private var bottomRightX: Any? = null
        private var bottomRightY: Any? = null
        private var bottomLeftX: Any? = null
        private var bottomLeftY: Any? = null

        fun topLeft(x: Int, y: Int) = apply { this.topLeftX = x; this.topLeftY = y }
        fun topLeft(x: Any, y: Any) = apply { this.topLeftX = x; this.topLeftY = y }

        fun topRight(x: Int, y: Int) = apply { this.topRightX = x; this.topRightY = y }
        fun topRight(x: Any, y: Any) = apply { this.topRightX = x; this.topRightY = y }

        fun bottomRight(x: Int, y: Int) = apply { this.bottomRightX = x; this.bottomRightY = y }
        fun bottomRight(x: Any, y: Any) = apply { this.bottomRightX = x; this.bottomRightY = y }

        fun bottomLeft(x: Int, y: Int) = apply { this.bottomLeftX = x; this.bottomLeftY = y }
        fun bottomLeft(x: Any, y: Any) = apply { this.bottomLeftX = x; this.bottomLeftY = y }

        override fun build(): Distort {
            // TODO compiler contracts
            validateAllNotNull(
                topLeftX,
                topLeftY,
                topRightX,
                topRightY,
                bottomRightX,
                bottomRightY,
                bottomLeftX,
                bottomLeftY
            )

            return Distort(
                topLeftX!!,
                topLeftY!!,
                topRightX!!,
                topRightY!!,
                bottomRightX!!,
                bottomRightY!!,
                bottomLeftX!!,
                bottomLeftY!!
            )
        }
    }
}