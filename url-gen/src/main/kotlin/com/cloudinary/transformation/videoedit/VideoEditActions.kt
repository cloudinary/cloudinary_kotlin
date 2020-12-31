package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.*
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
            duration?.let { Param("du", it) }
        ).toComponentString()
    }

    class Builder : TransformationComponentBuilder {
        private var startOffset: Any? = null
        private var endOffset: Any? = null
        private var duration: Any? = null

        fun startOffset(offset: Float) = apply { this.startOffset = offset }
        fun startOffset(offset: Int) = apply { this.startOffset = offset }
        fun startOffset(offset: String) = apply { this.startOffset = offset }
        fun endOffset(offset: Float) = apply { this.endOffset = offset }
        fun endOffset(offset: Int) = apply { this.endOffset = offset }
        fun endOffset(offset: String) = apply { this.endOffset = offset }
        fun duration(duration: Float) = apply { this.duration = duration }
        fun duration(duration: Int) = apply { this.duration = duration }
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
    private val source: VideoSource,
    private val transformation: Transformation? = null,
    private val transitionVideoPublicId: String? = null
) : VideoEdit() {
    override fun toString(): String {
        TODO("Not yet implemented")
    }

    // TODO
    class Builder(private val source: VideoSource) : TransformationComponentBuilder {
        override fun build(): Concatenate {
            return Concatenate(source)
        }
    }
}

