package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.gravity.CompassGravity
import com.cloudinary.transformation.gravity.Gravity

abstract class BasePad(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    val gravity: Gravity? = null,
    val offsetX: Any? = null,
    val offsetY: Any? = null,
    val background: Background? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "pad"

    override fun params(): Collection<Param?> {
        return super.params() + listOfNotNull(
            background?.let { Param("b", it) },
            gravity?.let { Param("g", it) },
            offsetX?.let { Param("x", it) },
            offsetY?.let { Param("y", it) })
    }

    abstract class BasePadBuilder : BaseBuilder<BasePadBuilder>() {
        protected var offsetX: Any? = null
        protected var offsetY: Any? = null
        protected var background: Background? = null

        fun background(background: Background) = apply {
            this.background = background
        }

        private fun offsetX(x: Any) = apply {
            this.offsetX = x
        }

        fun offsetX(x: String) = offsetX(x as Any)
        fun offsetX(x: Expression) = offsetX(x as Any)
        fun offsetX(x: Int) = offsetX(x as Any)
        fun offsetX(x: Float) = offsetX(x as Any)

        private fun offsetY(y: Any) = apply {
            this.offsetY = y
        }

        fun offsetY(y: String) = offsetY(y as Any)
        fun offsetY(y: Expression) = offsetY(y as Any)
        fun offsetY(y: Int) = offsetY(y as Any)
        fun offsetY(y: Float) = offsetY(y as Any)

        override fun getThis() = this
    }
}

class Pad(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    gravity: CompassGravity? = null,
    offsetX: Any? = null,
    offsetY: Any? = null,
    background: Background? = null
) :
    BasePad(dimensions, relative, regionRelative, gravity, offsetX, offsetY, background) {
    override val actionType = "pad"

    class Builder : BasePadBuilder() {
        private var gravity: CompassGravity? = null

        fun gravity(gravity: CompassGravity) = apply {
            this.gravity = gravity
        }

        override fun build() = Pad(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            gravity,
            offsetX,
            offsetY,
            background
        )
    }
}