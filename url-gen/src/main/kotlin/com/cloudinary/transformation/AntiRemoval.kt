package com.cloudinary.transformation

import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.LayerSource

class AntiRemoval private constructor(
    private val source: LayerSource,
    private val level: Any?,
    private val transformation: Transformation? = null, // imageTransformation
    private val position: LayerPosition? = null
) : Action {
    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            transformation,
            position,
            extras = listOfNotNull(Param("e", "anti_removal".joinWithValues(level)))
        )
    }

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
        private var level: Any? = null
        private var transformation: Transformation? = null
        private var position: LayerPosition? = null

        fun level(level: Int) = apply { this.level = level }
        fun level(level: Any) = apply { this.level = level }

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

        override fun build() = AntiRemoval(source, level, transformation, position)
    }
}