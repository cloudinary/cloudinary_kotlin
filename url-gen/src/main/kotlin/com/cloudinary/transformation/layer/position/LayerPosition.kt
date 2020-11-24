package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.gravity.GravityByDirection
import com.cloudinary.transformation.gravity.GravityByObjects

class LayerPosition private constructor(
    x: Any?,
    y: Any?,
    gravity: Gravity?,
    private val tileMode: TileMode?,
    private val allowOverflow: Boolean = false
) : BaseLayerPosition(x, y, gravity) {
    override fun asParams(): List<Param> {
        return mutableListOf<Param>().apply {
            addAll(super.asParams())
            if (tileMode == TileMode.TILED) add(Param("fl", "tiled"))
            if (!allowOverflow) add(Param("fl", "no_overflow"))
        }
    }

    @TransformationDsl
    class Builder {
        private var gravity: Gravity? = null
        private var x: Any? = null
        private var y: Any? = null
        private var tileMode: TileMode? = null
        private var allowOverflow: Boolean = true
        fun build() = LayerPosition(x, y, gravity, tileMode, allowOverflow)

        fun gravity(gravity: GravityByDirection) = apply {
            this.gravity = gravity
        }

        fun gravity(gravity: GravityByObjects) = apply {
            this.gravity = gravity
        }

        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun tileMode(tileMode: TileMode) = apply { this.tileMode = tileMode }
        fun allowOverflow(allowOverflow: Boolean = true) = apply {
            this.allowOverflow = allowOverflow
        }
    }
}

enum class TileMode {
    NONE,
    TILED;
}

