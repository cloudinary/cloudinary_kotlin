package com.cloudinary.transformation.resize

import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.Gravity

class LimitPad(
    dimensions: Dimensions,
    mode: ResizeMode? = null,
    ignoreAspectRatio: Boolean? = null,
    gravity: Gravity? = null,
    x: Any? = null,
    y: Any? = null,
    background: Background? = null
) : Pad(dimensions, mode, ignoreAspectRatio, gravity, x, y, background) {
    override val actionType = "lpad"

    class Builder : Pad.Builder() {
        override fun build() = LimitPad(
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