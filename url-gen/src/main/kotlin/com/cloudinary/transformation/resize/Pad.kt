package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Expression
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.cldAsX
import com.cloudinary.transformation.cldAsY
import com.cloudinary.transformation.gravity.Gravity

open class Pad(
    dimensions: Dimensions,
    mode: ResizeMode? = null,
    ignoreAspectRatio: Boolean? = null,
    val gravity: Gravity? = null,
    val x: Any? = null,
    val y: Any? = null,
    val background: Background? = null
) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "pad"

    override fun params(): Collection<Param?> {
        return super.params() + listOf(
            gravity,
            x?.cldAsX(),
            y?.cldAsY(),
            background?.toParam()
        )
    }

    open class Builder : BaseBuilder<Builder>() {
        protected var gravity: Gravity? = null
        protected var x: Any? = null
        protected var y: Any? = null
        protected var background: Background? = null

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        fun background(background: Background) = apply {
            this.background = background
        }

        private fun x(x: Any) = apply {
            this.x = x
        }

        fun x(x: String) = x(x as Any)
        fun x(x: Expression) = x(x as Any)
        fun x(x: Int) = x(x as Any)
        fun x(x: Float) = x(x as Any)

        private fun y(y: Any) = apply {
            this.y = y
        }

        fun y(y: String) = y(y as Any)
        fun y(y: Expression) = y(y as Any)
        fun y(y: Int) = y(y as Any)
        fun y(y: Float) = y(y as Any)

        override fun getThis() = this

        override fun build() = Pad(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio,
            gravity,
            x,
            y,
            background
        )
    }
}