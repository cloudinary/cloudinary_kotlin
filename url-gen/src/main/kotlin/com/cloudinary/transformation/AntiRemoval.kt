package com.cloudinary.transformation

import com.cloudinary.transformation.layer.BlendMode
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.layer.source.TextSource

class AntiRemoval private constructor(
    private val source: LayerSource,
    private val level: Any?,
    private val position: LayerPosition? = null,
    private val blendMode: BlendMode?
) : Action {

    companion object {
        fun source(source: LayerSource, options: (Builder.() -> Unit)? = null): AntiRemoval {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun image(options: (ImageBuilder.() -> Unit)? = null): AntiRemoval {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): AntiRemoval {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): AntiRemoval {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

    }

    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            position,
            blendMode = blendMode,
            extras = listOfNotNull(Param("e", "anti_removal".joinWithValues(level)))
        )
    }

    class FetchBuilder : BaseBuilder() {
        fun source(publicId: String, source: (FetchSource.Builder.() -> Unit)? = null) = apply {
            val builder = FetchSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: FetchSource) = apply { this.source = source }
    }

    class TextBuilder : BaseBuilder() {
        fun source(publicId: String, source: (TextSource.Builder.() -> Unit)? = null) = apply {
            val builder = TextSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: TextSource) = apply { this.source = source }
    }

    class ImageBuilder : BaseBuilder() {
        fun source(publicId: String, source: (ImageSource.Builder.() -> Unit)? = null) = apply {
            val builder = ImageSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: ImageSource) = apply { this.source = source }
    }

    class Builder(source: LayerSource) : BaseBuilder() {
        init {
            this.source = source
        }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var source: LayerSource? = null
        protected var position: LayerPosition? = null
        protected var blendMode: BlendMode? = null
        protected var level: Any? = null

        fun level(level: Int) = level(level as Any)
        fun level(level: Any) = apply { this.level = level }

        fun position(position: LayerPosition) = apply { this.position = position }
        fun position(position: (LayerPosition.Builder.() -> Unit)? = null) = apply {
            val builder = LayerPosition.Builder()
            position?.let { builder.it() }
            position(builder.build())
        }

        fun blendMode(blendMode: BlendMode) = apply { this.blendMode = blendMode }

        override fun build(): AntiRemoval {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            return AntiRemoval(safeSource, level, position, blendMode)
        }
    }
}