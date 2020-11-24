package com.cloudinary.transformation

import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.LayerSource

class Cutter private constructor(
    private val source: LayerSource,
    private val transformation: Transformation? = null, // imageTransformation
    private val position: LayerPosition? = null
) : Action {
    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            transformation,
            position,
            extras = listOf(Param("fl", "cutter"))
        )
    }

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var position: LayerPosition? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }

        fun transformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Transformation.Builder()
            builder.transformation()
            transformation(builder.build())
            return this
        }

        fun position(position: LayerPosition) = apply { this.position = position }

        fun position(position: (LayerPosition.Builder.() -> Unit)? = null): Builder {
            val builder = LayerPosition.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        override fun build() = Cutter(source, transformation, position)
    }
}
