package com.cloudinary.transformation

import com.cloudinary.transformation.Transformation.Builder
import com.cloudinary.transformation.layer.*

class Cutter private constructor(components: LayerComponents) : LayerAction(components) {

    class Builder(private val source: Layer) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var position: Position? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Builder()
            builder.transformation()
            transformation(builder.build())
            return this
        }

        fun position(position: Position) = apply { this.position = position }
        fun position(position: (Position.Builder.() -> Unit)? = null): Builder {
            val builder = Position.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        override fun build() =
            Cutter(
                buildLayerComponent(
                    source,
                    transformation,
                    position,
                    paramName = "layer",
                    paramKey = "l",
                    flag = Flag.Cutter()
                )
            )
    }
}