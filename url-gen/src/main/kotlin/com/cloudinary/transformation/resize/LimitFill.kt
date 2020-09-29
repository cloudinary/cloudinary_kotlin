package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.gravity.Gravity

class LimitFill(
    dimensions: Dimensions,
    mode: ResizeMode? = null,
    ignoreAspectRatio: Boolean? = null,
    val gravity: Gravity? = null
) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "lfill"

    override fun params(): Collection<Param?> {
        return super.params() + gravity
    }

    class Builder : BaseBuilder<Builder>() {
        private var gravity: Gravity? = null

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        override fun getThis() = this

        override fun build() = LimitFill(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio,
            gravity
        )
    }
}