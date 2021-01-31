package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.TextSource

class Underlay private constructor(
    private val source: Source,
    private val position: Position? = null,
    private val blendMode: BlendMode? = null
) : Action {

    init {
        require(source is ImageSource || source is FetchSource || source is TextSource) {
            "Source must be one of ImageSource, FetchSource or TextSource"
        }
    }

    companion object {
        fun image(options: (ImageBuilder.() -> Unit)? = null): Underlay {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): Underlay {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): Underlay {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun source(source: Source, options: (Builder.() -> Unit)? = null): Underlay {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "u",
            source,
            position,
            blendMode
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

    class Builder(source: Source) : BaseBuilder() {
        init {
            this.source = source
        }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var position: Position? = null
        protected var blendMode: BlendMode? = null
        protected var source: Source? = null

        fun position(position: Position) = apply { this.position = position }
        fun position(position: (Position.Builder.() -> Unit)? = null) = apply {
            val builder = Position.Builder()
            position?.let { builder.it() }
            position(builder.build())
        }

        fun blendMode(blendMode: BlendMode) = apply { this.blendMode = blendMode }

        override fun build(): Underlay {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            return Underlay(safeSource, position, blendMode)
        }
    }
}
