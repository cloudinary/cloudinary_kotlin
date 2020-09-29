package com.cloudinary.transformation.resize

import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.Gravity

class MinimumPad(
    dimensions: Dimensions,
    mode: ResizeMode? = null,
    ignoreAspectRatio: Boolean? = null,
    gravity: Gravity? = null,
    x: Any? = null,
    y: Any? = null,
    background: Background? = null
) : Pad(dimensions, mode, ignoreAspectRatio, gravity, x, y, background) {
    override val actionType = "mpad"

    class Builder : Pad.Builder() {
        override fun build() = MinimumPad(
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