package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.layer.source.VideoSource

abstract class VideoEdit : Action {
    companion object {

        fun trim(options: Trim.Builder.() -> Unit): Trim {
            val builder = Trim.Builder()
            builder.options()
            return builder.build()
        }

        fun volume(level: Int) = Volume(level)
        fun volume(volume: Volume) = volume

        fun concatenate(source: VideoSource, options: (Concatenate.Builder.() -> Unit)? = null): Concatenate {
            val builder = Concatenate.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}
