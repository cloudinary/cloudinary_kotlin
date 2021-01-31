package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.gravity.CompassGravity
import com.cloudinary.transformation.gravity.FocusOnGravity
import com.cloudinary.transformation.gravity.Gravity

class Position private constructor(
    offsetX: Any?,
    offsetY: Any?,
    gravity: Gravity?,
    private val tiled: Boolean?,
    private val allowOverflow: Boolean = false
) : BaseLayerPosition(offsetX, offsetY, gravity) {
    init {
        require(
            gravity == null ||
                    gravity is FocusOnGravity ||
                    gravity is CompassGravity
        ) {
            "When provided, gravity must be either CompassGravity or FocusOnGravity"
        }
    }

    private constructor(position: Position) : this(
        position.offsetX,
        position.offsetY,
        position.gravity,
        position.tiled,
        position.allowOverflow
    )

    constructor(options: Builder.() -> Unit) : this(fromBuilder(options))

    override fun asParams(): List<Param> {
        return mutableListOf<Param>().apply {
            addAll(super.asParams())
            if (tiled == true) add(Param("fl", "tiled"))
            if (!allowOverflow) add(Param("fl", "no_overflow"))
        }
    }

    @TransformationDsl
    class Builder {
        private var gravity: Gravity? = null
        private var offsetX: Any? = null
        private var offsetY: Any? = null
        private var tiled: Boolean? = null
        private var allowOverflow: Boolean = true

        fun build() = Position(offsetX, offsetY, gravity, tiled, allowOverflow)

        fun gravity(gravity: CompassGravity) = apply {
            this.gravity = gravity
        }

        fun gravity(gravity: FocusOnGravity) = apply {
            this.gravity = gravity
        }

        fun offsetX(offsetX: Int) = apply { this.offsetX = offsetX }
        fun offsetX(offsetX: Any) = apply { this.offsetX = offsetX }
        fun offsetY(offsetY: Int) = apply { this.offsetY = offsetY }
        fun offsetY(offsetY: Any) = apply { this.offsetY = offsetY }

        fun tiled() = apply { this.tiled = true }

        fun allowOverflow(allowOverflow: Boolean = true) = apply {
            this.allowOverflow = allowOverflow
        }
    }
}

private fun fromBuilder(options: Position.Builder.() -> Unit): Position {
    val builder = Position.Builder()
    builder.options()
    return builder.build()
}