package com.cloudinary.transformation

import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.*

class Displace private constructor(components: LayerComponents) : LayerContainer(components) {

    companion object {
        fun displace(source: Layer, transformation: Transformation? = null, position: Position? = null) =
            Displace(
                buildLayerComponent(
                    source, transformation, position, paramName = "layer", paramKey = "l", extraParams = Effect(
                        "displace"
                    ).params.values
                )
            )
    }

    class Builder private constructor(
        private val source: Layer,
        private var transformation: Transformation? = null,
        private var position: Position? = null
    ) : TransformationComponentBuilder {
        constructor(layer: Layer) : this(layer, null, null)

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun position(position: (Position.Builder.() -> Unit)? = null): Builder {
            val builder = Position.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        fun position(position: Position) = apply { this.position = position }
        override fun build() = displace(source, transformation, position)
    }
}

