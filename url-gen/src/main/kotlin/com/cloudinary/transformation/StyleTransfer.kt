package com.cloudinary.transformation

import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.util.cldRanged

class StyleTransfer private constructor(component: TransformationComponent) : Layer(component) {

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
        private var transformation: TransformationComponent? = null
        private var preserveColor: Boolean = false
        private var strength: Int? = null

        fun setTransformation(transformation: TransformationComponent) = apply { this.transformation = transformation }
        fun setTransformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Transformation.Builder()
            builder.transformation()
            setTransformation(builder.build())
            return this
        }

        fun setPreserveColor(preserveColor: Boolean) = apply { this.preserveColor = preserveColor }
        fun setStrength(strength: Int) = apply { this.strength = strength }

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