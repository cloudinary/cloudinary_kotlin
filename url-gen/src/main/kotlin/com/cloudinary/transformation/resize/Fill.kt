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
        private var x: Any? = null
        private var y: Any? = null

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        private fun x(x: Any) = apply {
            this.x = x
        }
        //Only used in conjunction with gravity(Gravity.xyCenter())
        fun x(x: String) = x(x as Any)
        fun x(x: Expression) = x(x as Any)
        fun x(x: Int) = x(x as Any)
        fun x(x: Float) = x(x as Any)

        @Deprecated("This function will be removed in the next major version, use x instead", replaceWith = ReplaceWith("x(x)"))
        fun offsetX(x: String) = x(x as Any)
        @Deprecated("This function will be removed in the next major version, use x instead", replaceWith = ReplaceWith("x(x)"))
        fun offsetX(x: Expression) = x(x as Any)
        @Deprecated("This function will be removed in the next major version, use x instead", replaceWith = ReplaceWith("x(x)"))
        fun offsetX(x: Int) = x(x as Any)
        @Deprecated("This function will be removed in the next major version, use x instead", replaceWith = ReplaceWith("x(x)"))
        fun offsetX(x: Float) = x(x as Any)

        private fun y(y: Any) = apply {
            this.y = y
        }
        //Only used in conjunction with gravity(Gravity.xyCenter())
        fun y(y: String) = y(y as Any)
        fun y(y: Expression) = y(y as Any)
        fun y(y: Int) = y(y as Any)
        fun y(y: Float) = y(y as Any)

        @Deprecated("This function will be removed in the next major version, use y instead", replaceWith = ReplaceWith("y(y)"))
        fun offsetY(y: String) = y(y as Any)
        @Deprecated("This function will be removed in the next major version, use y instead", replaceWith = ReplaceWith("y(y)"))
        fun offsetY(y: Expression) = y(y as Any)
        @Deprecated("This function will be removed in the next major version, use y instead", replaceWith = ReplaceWith("y(y)"))
        fun offsetY(y: Int) = y(y as Any)
        @Deprecated("This function will be removed in the next major version, use y instead", replaceWith = ReplaceWith("y(y)"))
        fun offsetY(y: Float) = y(y as Any)

        override fun getThis() = this

        override fun build() = Fill(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            gravity,
            x,
            y
        )
    }
}