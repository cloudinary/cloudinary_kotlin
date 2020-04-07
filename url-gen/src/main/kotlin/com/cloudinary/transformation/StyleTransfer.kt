package com.cloudinary.transformation

import com.cloudinary.transformation.Transformation.Builder
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerComponents
import com.cloudinary.transformation.layer.LayerContainer
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.util.cldRanged

class StyleTransfer private constructor(components: LayerComponents) : LayerContainer(components) {

    class Builder(private val source: Layer) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var preserveColor: Boolean = false
        private var strength: Int? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Builder()
            builder.transformation()
            transformation(builder.build())
            return this
        }

        fun preserveColor(preserveColor: Boolean) = apply { this.preserveColor = preserveColor }
        fun strength(strength: Int) = apply { this.strength = strength }

        override fun build() =
            StyleTransfer(
                buildLayerComponent(
                    source, transformation, paramName = "layer", paramKey = "l", extraParams = Effect(
                        "style_transfer",
                        listOfNotNull(strength?.cldRanged(0, 100), if (preserveColor) "preserve_color" else null)
                    ).params.values
                )
            )
    }
}