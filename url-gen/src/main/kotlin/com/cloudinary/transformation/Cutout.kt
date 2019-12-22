package com.cloudinary.transformation

import com.cloudinary.transformation.Transformation.Builder
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.layer.buildLayerComponent

class Cutout private constructor(components: List<TransformationComponent>) : Layer(components) {

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
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
            Cutout(
                buildLayerComponent(
                    source, transformation, position, paramName = "layer", paramKey = "l", extraParams = Effect(
                        "cut_out"
                    ).params.values
                )
            )
    }
}