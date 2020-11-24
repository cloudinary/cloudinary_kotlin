package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.position.TimelinePosition
import com.cloudinary.transformation.layer.position.VideoPosition
import com.cloudinary.transformation.layer.source.*

class NonVideoOnImageOverlay(
    private val source: LayerSource,
    private val transformation: Transformation? = null, // imageTransformation
    private val position: LayerPosition? = null,
    private val blendMode: BlendMode? = null
) : Overlay() {
    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            transformation,
            position,
            blendMode
        )
    }

    class Builder internal constructor(private val source: LayerSource) : TransformationComponentBuilder {
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

        override fun build() = NonVideoOnImageOverlay(source, transformation, position, blendMode)
    }
}

class NonVideoOnVideoOverlay(
    private val source: LayerSource,
    private val transformation: Transformation? = null, // imageTransformation
    private val position: VideoPosition? = null,
    private val timelinePosition: TimelinePosition? = null
) : Overlay() {
    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            transformation,
            position,
            timelinePosition = timelinePosition
        )
    }

    class Builder(private val source: LayerSource) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var position: VideoPosition? = null
        private var timelinePosition: TimelinePosition? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }

        fun transformation(options: Transformation.Builder.() -> Unit): Builder {
            val builder = Transformation.Builder()
            builder.options()
            transformation(builder.build())
            return this
        }

        fun position(position: VideoPosition) = apply { this.position = position }

        fun position(position: (VideoPosition.Builder.() -> Unit)? = null): Builder {
            val builder = VideoPosition.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        fun timelinePosition(timelinePosition: TimelinePosition) = apply { this.timelinePosition = timelinePosition }

        override fun build() = NonVideoOnVideoOverlay(source, transformation, position, timelinePosition)
    }
}

class VideoOverlay(
    private val source: BaseVideoSource,
    private val transformation: Transformation? = null, // videoTransformation
    private val position: VideoPosition? = null,
    private val timelinePosition: TimelinePosition? = null
) : Overlay() {
    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            transformation,
            position,
            timelinePosition = timelinePosition
        )
    }

    class Builder(private val source: BaseVideoSource) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var position: VideoPosition? = null
        private var timelinePosition: TimelinePosition? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }

        fun transformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Transformation.Builder()
            builder.transformation()
            transformation(builder.build())
            return this
        }

        fun position(position: VideoPosition) = apply { this.position = position }

        fun position(position: (VideoPosition.Builder.() -> Unit)? = null): Builder {
            val builder = VideoPosition.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        fun timelinePosition(timelinePosition: TimelinePosition) = apply { this.timelinePosition = timelinePosition }

        override fun build() = VideoOverlay(source, transformation, position, timelinePosition)
    }
}

abstract class Overlay : Action {
    companion object {
        fun imageOnImage(
            publicId: String,
            options: (NonVideoOnImageOverlay.Builder.() -> Unit)? = null
        ) = imageOnImage(ImageSource((publicId)), options)

        fun imageOnImage(
            source: ImageSource,
            options: (NonVideoOnImageOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnImageOverlay {
            val builder = NonVideoOnImageOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetchOnImage(
            url: String,
            options: (NonVideoOnImageOverlay.Builder.() -> Unit)? = null
        ) = fetchOnImage(FetchSource((url)), options)

        fun fetchOnImage(
            source: FetchSource,
            options: (NonVideoOnImageOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnImageOverlay {
            val builder = NonVideoOnImageOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun textOnImage(
            source: TextSource,
            options: (NonVideoOnImageOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnImageOverlay {
            val builder = NonVideoOnImageOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun textOnImage(source: TextSource): NonVideoOnImageOverlay {
            return NonVideoOnImageOverlay(source)
        }

        fun imageOnVideo(
            source: ImageSource,
            options: (NonVideoOnVideoOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnVideoOverlay {
            val builder = NonVideoOnVideoOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun imageOnVideo(
            publicId: String,
            options: (NonVideoOnVideoOverlay.Builder.() -> Unit)? = null
        ) = imageOnVideo(ImageSource(publicId), options)

        fun textOnVideo(
            source: TextSource,
            options: (NonVideoOnVideoOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnVideoOverlay {
            val builder = NonVideoOnVideoOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun subtitles(
            source: SubtitlesSource,
            options: (VideoOverlay.Builder.() -> Unit)? = null
        ): VideoOverlay {
            val builder = VideoOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetchOnVideo(
            url: String, options: (NonVideoOnVideoOverlay.Builder.() -> Unit)? = null
        ) = fetchOnVideo(FetchSource(url), options)

        fun fetchOnVideo(
            source: FetchSource,
            options: (NonVideoOnVideoOverlay.Builder.() -> Unit)? = null
        ): NonVideoOnVideoOverlay {
            val builder = NonVideoOnVideoOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun video(
            publicId: String,
            options: (VideoOverlay.Builder.() -> Unit)? = null
        ) = video(VideoSource(publicId), options)

        fun video(
            source: VideoSource,
            options: (VideoOverlay.Builder.() -> Unit)? = null
        ): VideoOverlay {
            val builder = VideoOverlay.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}