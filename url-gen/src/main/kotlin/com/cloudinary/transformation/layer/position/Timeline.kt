package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.TransformationDsl

class Timeline private constructor(
    val startOffset: Any?,
    val duration: Any?,
    val endOffset: Any?
) {
    companion object {
        fun position(options: Builder.() -> Unit): Timeline {
            val builder = Builder()
            builder.options()
            return builder.build()
        }
    }

    @TransformationDsl
    class Builder {
        private var startOffset: Any? = null
        private var endOffset: Any? = null
        private var duration: Any? = null

        fun build() = Timeline(startOffset, duration, endOffset)

        fun startOffset(startOffset: Float) = apply { this.startOffset = startOffset }
        fun endOffset(endOffset: Float) = apply { this.endOffset = endOffset }
        fun duration(duration: Float) = apply { this.duration = duration }
    }
}
