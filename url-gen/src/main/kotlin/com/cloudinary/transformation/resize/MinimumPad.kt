package com.cloudinary.transformation.resize

import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.CompassGravity

class MinimumPad(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    gravity: CompassGravity? = null,
    offsetX: Any? = null,
    offsetY: Any? = null,
    background: Background? = null
) : BasePad(dimensions, relative, regionRelative, gravity, offsetX, offsetY, background) {
    override val actionType = "mpad"

    class Builder : BasePadBuilder() {
        private var gravity: CompassGravity? = null

        fun gravity(gravity: CompassGravity) = apply { this.gravity = gravity }

        override fun build() = MinimumPad(
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