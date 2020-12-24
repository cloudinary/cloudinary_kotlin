package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.gravity.Gravity

class LimitFill(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    val gravity: Gravity? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "lfill"

    override fun params(): Collection<Param?> {
        return if (gravity == null) super.params() else super.params() + Param("g", gravity)
    }

    class Builder : BaseBuilder<Builder>() {
        private var gravity: Gravity? = null

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        override fun getThis() = this

        override fun build() = LimitFill(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            gravity
        )
    }
}