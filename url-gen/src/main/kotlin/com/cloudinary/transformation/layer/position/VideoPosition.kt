package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.gravity.GravityByDirection

class VideoPosition private constructor(
    x: Any?,
    y: Any?,
    gravity: GravityByDirection?
) :
    BaseLayerPosition(x, y, gravity) {

    @TransformationDsl
    class Builder {
        private var gravity: GravityByDirection? = null
        private var x: Any? = null
        private var y: Any? = null

        fun build() = VideoPosition(x, y, gravity)

        fun gravity(gravity: GravityByDirection) = apply {
            this.gravity = gravity
        }

        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
    }
}
