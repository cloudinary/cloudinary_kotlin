package com.cloudinary.transformation

import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.layer.buildLayerComponent

class Displace private constructor(component: TransformationComponent) : Layer(component) {

    companion object {
        fun displace(source: LayerSource, transformation: TransformationComponent? = null, position: Position? = null) =
            Displace(
                buildLayerComponent(
                    source, transformation, position, paramName = "layer", paramKey = "l", extraParams = Effect(
                        "displace"
                    ).params.values
                )
            )

    }

    class Builder private constructor(
        private val source: LayerSource,
        private var transformation: TransformationComponent? = null,
        private var position: Position? = null
    ) : TransformationComponentBuilder {
        constructor(layerSource: LayerSource) : this(layerSource, null, null)

        fun setTransformation(transformation: TransformationComponent) = apply { this.transformation = transformation }
        fun setPosition(position: (Position.Builder.() -> Unit)? = null): Builder {
            val builder = Position.Builder()
            position?.let { builder.it() }
            setPosition(builder.build())
            return this
        }

        fun setPosition(position: Position) = apply { this.position = position }
        override fun build() = displace(source, transformation, position)
    }
}

