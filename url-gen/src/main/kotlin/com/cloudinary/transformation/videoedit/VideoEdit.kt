package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.effect.Waveform
import com.cloudinary.transformation.effect.buildEffect
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.VideoSource

abstract class VideoEdit : Action {
    companion object {

        fun trim(options: Trim.Builder.() -> Unit): Trim {
            val builder = Trim.Builder()
            builder.options()
            return builder.build()
        }

        fun volume(level: Int) = Volume(level)
        fun volume(level: Any) = Volume(level)
        fun volume(volume: Volume) = volume

        private fun concatenate(source: Source, options: (Concatenate.Builder.() -> Unit)? = null): Concatenate {
            val builder = Concatenate.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun concatenate(source: ImageSource, options: (Concatenate.Builder.() -> Unit)? = null) =
            concatenate(source as Source, options)

        fun concatenate(source: VideoSource, options: (Concatenate.Builder.() -> Unit)? = null) =
            concatenate(source as Source, options)

        fun concatenate(source: FetchSource, options: (Concatenate.Builder.() -> Unit)? = null) =
            concatenate(source as Source, options)

        fun preview(options: (Preview.Builder.() -> Unit)? = null): Preview {
            val builder = Preview.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun waveform(format: Format, options: (Waveform.Builder.() -> Unit)? = null) : Effect {
            if (options == null) {
                return Waveform(format)
            }
            return buildEffect(Waveform(format).Builder(), options)
        }
    }

}
