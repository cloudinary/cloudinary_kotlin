package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.position.Timeline
import com.cloudinary.transformation.layer.source.*

class Overlay private constructor(
    private val source: Source,
    private val position: Position? = null,
    private val timeline: Timeline? = null,
    private val blendMode: BlendMode? = null
) : Action {
    companion object {
        fun image(options: (ImageBuilder.() -> Unit)? = null): Overlay {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun video(options: (VideoBuilder.() -> Unit)? = null): Overlay {
            val builder = VideoBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): Overlay {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): Overlay {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun subtitles(options: (SubtitlesBuilder.() -> Unit)? = null): Overlay {
            val builder = SubtitlesBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun source(source: Source, options: (Builder.() -> Unit)? = null): Overlay {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            position,
            blendMode,
            timeline
        )
    }

    class VideoBuilder : BaseBuilder() {
        fun source(publicId: String, source: (VideoSource.Builder.() -> Unit)? = null) = apply {
            val builder = VideoSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: VideoSource) = apply { this.source = source }
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

    class SubtitlesBuilder : BaseBuilder() {
        fun source(publicId: String, source: (SubtitlesSource.Builder.() -> Unit)? = null) = apply {
            val builder = SubtitlesSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: SubtitlesSource) = apply { this.source = source }
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
        protected var timeline: Timeline? = null
        protected var source: Source? = null

        fun timeline(timeline: Timeline) = apply { this.timeline = timeline }
        fun position(position: Position) = apply { this.position = position }
        fun position(position: (Position.Builder.() -> Unit)? = null) = apply {
            val builder = Position.Builder()
            position?.let { builder.it() }
            position(builder.build())
        }

        fun blendMode(blendMode: BlendMode) = apply { this.blendMode = blendMode }

        override fun build(): Overlay {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            return Overlay(safeSource, position, timeline, blendMode)
        }
    }
}