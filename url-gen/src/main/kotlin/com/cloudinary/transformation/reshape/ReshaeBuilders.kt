package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.util.validateAllNotNull

class ShearBuilder : TransformationComponentBuilder {
    private var skewX: Any? = null
    private var skewY: Any? = null

    fun skewX(skewX: Int) = apply { this.skewX = skewX }

    fun skewY(skewY: Int) = apply { this.skewY = skewY }

    override fun build(): Reshape {
        validateAllNotNull(skewX, skewY)
        return reshape("shear", skewX, skewY)
    }
}

class DistortBuilder : TransformationComponentBuilder {
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

    override fun build(): Reshape {
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

        return reshape(
            "distort",
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
}