package com.cloudinary.transformation

import com.cloudinary.transformation.Transformation.Builder
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.util.cldRanged

class AntiRemoval private constructor(components: List<TransformationComponent>) : Layer(components) {

    class Builder(private var source: LayerSource) : TransformationComponentBuilder {
        private var level: Any? = null
        private var transformation: Transformation? = null
        private var position: Position? = null

        fun setLevel(level: Int) = apply { this.level = level }
        fun setLevel(level: Any) = apply { this.level = level }
        fun setTransformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun setTransformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Builder()
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
            AntiRemoval(
                buildLayerComponent(
                    source,
                    transformation,
                    position,
                    null,
                    "overlay",
                    "l",
                    extraParams = Effect(
                        "anti_removal",
                        level?.cldRanged(1, 100)
                    ).params.values
                )
            )
    }
}