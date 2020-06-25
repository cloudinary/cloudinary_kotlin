package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.gravity.Gravity

class Position(internal val action: ParamsAction) : Action by action {
    companion object {
        internal fun position(options: Builder.() -> Unit): Position {
            val builder = Builder()
            builder.options()
            return builder.build()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var gravity: Gravity? = null

        private var x: Any? = null

        private var y: Any? = null

        private var tileMode: TileMode? = null

        private var allowOverflow: Boolean = true

        override fun build() = Position(
            ParamsAction(
                listOfNotNull(
                    gravity,
                    x?.cldAsX(),
                    y?.cldAsY(),
                    tileMode?.asFlag(),
                    if (!allowOverflow) FlagsParam(Flag.noOverflow()) else null
                )
            )
        )

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        fun x(x: Int) = apply { this.x = x }

        fun x(x: Any) = apply { this.x = x }

        fun y(y: Int) = apply { this.y = y }

        fun y(y: Any) = apply { this.y = y }

        fun tileMode(tileMode: TileMode) = apply { this.tileMode = tileMode }

        fun allowOverflow(allowOverflow: Boolean) = apply {
            this.allowOverflow = allowOverflow
        }
    }
}

enum class TileMode {
    NONE,
    TILED;

    fun asFlag() = if (this == TILED) FlagsParam(Flag.tiled()) else null
}