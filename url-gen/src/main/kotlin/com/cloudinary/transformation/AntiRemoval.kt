package com.cloudinary.transformation

import com.cloudinary.transformation.Transformation.Builder
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.*
import com.cloudinary.util.cldRanged

class AntiRemoval private constructor(components: LayerComponents) : LayerContainer(components) {

    class Builder(private var source: Layer) : TransformationComponentBuilder {
        private var level: Any? = null
        private var transformation: Transformation? = null
        private var position: Position? = null

        fun level(level: Int) = apply { this.level = level }
        fun level(level: Any) = apply { this.level = level }
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