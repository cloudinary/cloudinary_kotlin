package com.cloudinary.transformation.resize

import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.AutoGravity

class FillPad(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    gravity: AutoGravity? = null,
    offsetX: Any? = null,
    offsetY: Any? = null,
    background: Background? = null
) : BasePad(dimensions, relative, regionRelative, gravity, offsetX, offsetY, background) {
    override val actionType = "fill_pad"

    class Builder : BasePad.BasePadBuilder() {
        private var gravity: AutoGravity? = null

        fun gravity(gravity: AutoGravity) = apply { this.gravity = gravity }

        override fun build() = FillPad(
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