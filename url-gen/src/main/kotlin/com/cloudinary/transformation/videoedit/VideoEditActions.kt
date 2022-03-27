package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.*
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.VideoSource

class Trim(
    private val startOffset: Any? = null,
    private val endOffset: Any? = null,
    private val duration: Any? = null
) : VideoEdit() {

    init {
        require(listOfNotNull(startOffset, endOffset, duration).size in 1..2)
    }

    override fun toString(): String {
        return listOfNotNull(
            endOffset?.let { Param("eo", it) },
            startOffset?.let { Param("so", it) },
            duration?.let { Param("du", it.toString().replace("%", "p")) }
        ).toComponentString()
    }

    class Builder : TransformationComponentBuilder {
        private var startOffset: Any? = null
        private var endOffset: Any? = null
        private var duration: Any? = null

        fun startOffset(offset: Float) = apply { this.startOffset = offset }
        fun startOffset(offset: String) = apply { this.startOffset = offset }
        fun startOffset(offset: Expression) = apply { this.startOffset = offset }
        fun endOffset(offset: Float) = apply { this.endOffset = offset }
        fun endOffset(offset: String) = apply { this.endOffset = offset }
        fun endOffset(offset: Expression) = apply { this.endOffset = offset }
        fun duration(duration: Float) = apply { this.duration = duration }
        fun duration(duration: String) = apply { this.duration = duration }

        override fun build(): Trim {
            return Trim(startOffset, endOffset, duration)
        }
    }
}

class Volume internal constructor(private val value: Any, private val suffix: String? = null) : VideoEdit() {
    companion object {
        fun mute() = Volume("mute")
        fun byPercent(percentage: Int) = Volume(percentage)
        fun byDecibels(decibels: Int) = Volume(decibels, "db")
    }

    override fun toString(): String {
        return "e_volume".joinWithValues(value) + (suffix ?: "")
    }

}

class Concatenate(
    private val source: Source,
    private val transition: VideoSource? = null,
    private val prepend: Boolean? = null,
    private val duration: Any? = null
) : VideoEdit() {

    init {
        require(source is VideoSource || duration != null) { "When an image source is provided duration cannot be empty" }
    }

    companion object {
        fun imageSource(publicId: String, options: (ImageSource.Builder.() -> Unit)? = null): ImageSource {
            val builder = ImageSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }

        fun videoSource(publicId: String, options: (VideoSource.Builder.() -> Unit)? = null): VideoSource {
            val builder = VideoSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetchSource(url: String, options: (FetchSource.Builder.() -> Unit)? = null): FetchSource {
            val builder = FetchSource.Builder(url)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {

        val sourceComponent = (if (transition == null) "fl_splice," else "") +
                (duration?.let { "du_$duration," } ?: "") +
                "l_$source"
        val lastComponent = "fl_layer_apply" + (prepend?.let { ",so_0" } ?: "")
        val transitionComponent = transition?.let {
            "e_transition,l_$transition".joinWithValues(transition.transformation, "fl_layer_apply", separator = "/")
        }
        return sourceComponent.joinWithValues(
            source.transformation,
            transitionComponent,
            lastComponent,
            separator = DEFAULT_COMPONENT_SEPARATOR
        )
    }

    class Builder(private val source: Source) : TransformationComponentBuilder {
        private var transition: VideoSource? = null
        private var prepend: Boolean? = null
        private var duration: Any? = null

        fun transition(transition: VideoSource) = apply { this.transition = transition }
        fun prepend(prepend: Boolean = true) = apply { this.prepend = prepend }
        fun duration(duration: Any) = apply { this.duration = duration }

        override fun build(): Concatenate {
            return Concatenate(source, transition, prepend, duration)
        }
    }
}

class Transition {
    companion object {
        fun videoSource(publicId: String, options: (VideoSource.Builder.() -> Unit)? = null): VideoSource {
            val builder = VideoSource.Builder(publicId)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

class Preview private constructor(
    private val duration: Float?,
    private val maximumSegments: Int?,
    private val minimumSegmentDuration: Any?
) : VideoEdit() {
    override fun toString(): String {
        return "e_preview".joinWithValues(
            duration?.let { "duration_$it" },
            maximumSegments?.let { "max_seg_$it" },
            minimumSegmentDuration?.let { "min_seg_dur_$it" }
        )
    }

    class Builder : TransformationComponentBuilder {

        private var duration: Float? = null
        private var maximumSegments: Int? = null
        private var minimumSegmentDuration: Any? = null

        fun duration(duration: Float) = apply { this.duration = duration }
        fun maximumSegments(maximumSegments: Int) = apply { this.maximumSegments = maximumSegments }

        fun minimumSegmentDuration(minimumSegmentDuration: String) =
            apply { this.minimumSegmentDuration = minimumSegmentDuration }

        fun minimumSegmentDuration(minimumSegmentDuration: Any) =
            apply { this.minimumSegmentDuration = minimumSegmentDuration }

        override fun build() = Preview(duration, maximumSegments, minimumSegmentDuration)
    }
}


