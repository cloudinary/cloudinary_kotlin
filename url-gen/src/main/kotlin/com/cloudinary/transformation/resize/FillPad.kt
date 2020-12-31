package com.cloudinary.transformation.resize

import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.CompassGravity

class FillPad(
    dimensions: Dimensions,
    relative: Boolean? = null,
    regionRelative: Boolean? = null,
    gravity: CompassGravity? = null,
    x: Any? = null,
    y: Any? = null,
    background: Background? = null
) : Pad(dimensions, relative, regionRelative, gravity, x, y, background) {
    override val actionType = "fill_pad"

    class Builder : Pad.Builder() {
        override fun build() = FillPad(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            gravity,
            x,
            y,
            background
        )
    }
}