package com.cloudinary.transformation

import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.layer.buildLayerComponent

class Cutter private constructor(component: TransformationComponent) : Layer(component) {

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
        private var transformation: TransformationComponent? = null
        private var position: Position? = null

        fun setTransformation(transformation: TransformationComponent) = apply { this.transformation = transformation }
        fun setTransformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Transformation.Builder()
            builder.transformation()
            setTransformation(builder.build())
            return this
        }

        fun setPosition(position: Position) = apply { this.position = position }
        fun setPosition(position: (Position.Builder.() -> Unit)? = null): Builder {
            val builder = Position.Builder()
            position?.let { builder.it() }
            setPosition(builder.build())
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
                    flag = FlagKey.CUTTER()
                )
            )
    }
}