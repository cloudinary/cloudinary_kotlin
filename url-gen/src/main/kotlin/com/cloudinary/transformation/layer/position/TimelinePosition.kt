package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.TransformationDsl

class TimelinePosition private constructor(
    val startOffset: Any?,
    val duration: Any?
) {
    @TransformationDsl
    class Builder {
        private var startOffset: Any? = null
        private var duration: Any? = null

        fun build() = TimelinePosition(startOffset, duration)

        fun startOffset(startOffset: Float) = apply { this.startOffset = startOffset }
        fun duration(duration: Float) = apply { this.duration = duration }
    }
}
