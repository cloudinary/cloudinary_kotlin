package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.layer.source.TextSource

class Underlay(
    private val source: LayerSource,
    private val transformation: Transformation? = null, // imageTransformation
    private val position: LayerPosition? = null,
    private val blendMode: BlendMode? = null
) : Action {

    companion object {
        fun image(
            publicId: String,
            options: (Builder.() -> Unit)? = null
        ) = image(ImageSource((publicId)), options)

        fun image(
            source: ImageSource,
            options: (Builder.() -> Unit)? = null
        ): Underlay {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(
            url: String,
            options: (Builder.() -> Unit)? = null
        ) = fetch(FetchSource((url)), options)

        fun fetch(
            source: FetchSource,
            options: (Builder.() -> Unit)? = null
        ): Underlay {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(
            source: TextSource,
            options: (Builder.() -> Unit)? = null
        ): Underlay {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "u",
            source,
            transformation,
            position,
            blendMode
        )
    }

    @TransformationDsl
    class Builder(private val source: LayerSource) {
        private var transformation: Transformation? = null
        private var position: LayerPosition? = null
        private var blendMode: BlendMode? = null

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

        fun blendMode(blendMode: BlendMode) = apply { this.blendMode = blendMode }

        fun build() = Underlay(source, transformation, position, blendMode)
    }
}