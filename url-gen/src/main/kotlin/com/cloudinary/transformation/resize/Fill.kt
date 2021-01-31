package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.gravity.Gravity

class Fill(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    val gravity: Gravity? = null,
    val x: Any? = null,
    val y: Any? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "fill"

    override fun params(): Collection<Param?> {
        return super.params() + listOfNotNull(
            gravity?.let { Param("g", it) },
            x?.let { Param("x", it) },
            y?.let { Param("y", it) }
        )
    }

    class Builder : BaseBuilder<Builder>() {
        private var gravity: Gravity? = null
        private var offsetX: Any? = null
        private var offsetY: Any? = null

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
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

        override fun build() = Fill(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            gravity,
            offsetX,
            offsetY
        )
    }
}