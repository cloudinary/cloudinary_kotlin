package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.*
import com.cloudinary.transformation.layer.LayerAction
import com.cloudinary.transformation.layer.MediaSource

class VideoEdit(action: Action) : Action by action {
    companion object {

        fun trim(options: TrimBuilder.() -> Unit): VideoEdit {
            val builder = TrimBuilder()
            builder.options()
            return builder.build()
        }

        fun volume(level: Int) = VideoEdit(ParamsAction(Volume.level(level)))
        fun volume(volume: Volume) = VideoEdit(ParamsAction(volume))

        // TODO transition
        fun concatenate(source: MediaSource, options: (LayerAction.Builder.() -> Unit)? = null): VideoEdit {
            val builder = LayerAction.Builder(source)
            options?.let { builder.it() }
            builder.flagKey(Flag.splice())
            return VideoEdit(builder.build())
        }
    }
}

// TODO this isn't great code, redesign
class Volume private constructor(vararg values: Any) :
    Param("volume", "e", ParamValue(values)) {
    companion object {
        fun mute() = Volume("volume", "mute")
        fun level(level: Int) = Volume("volume", level)
    }
}
