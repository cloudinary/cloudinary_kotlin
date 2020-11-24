package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.Action

abstract class Transcode : Action {
    companion object {
        fun streamingProfile(profile: StreamingProfileType) = StreamingProfile(profile)
        fun keyframeInterval(seconds: Float) = KeyframeInterval(seconds)
        fun fps(fps: Float) = Fps.Builder().fixed(fps).build()
        fun fps(min: Float, max: Float) = Fps.Builder().min(min).max(max).build()
        fun fps(options: (Fps.Builder.() -> Unit)? = null): Fps {
            val builder = Fps.Builder()
            options?.let { builder.options() }
            return builder.build()
        }

        fun bitRate(bitrate: String, options: (Bitrate.Builder.() -> Unit)? = null): Bitrate {
            val builder = Bitrate.Builder(bitrate)
            options?.let { builder.it() }
            return builder.build()
        }


        fun audioFrequency(frequency: AudioFrequencyType) = AudioFrequency(frequency)

        fun audioCodec(codec: AudioCodecType) = AudioCodec(codec)
        fun toAnimated(format: String, options: (ToAnimated.Builder.() -> Unit)? = null): Transcode {
            val builder = ToAnimated.Builder(format)
            options?.let { builder.it() }
            return builder.build()
        }

        fun videoCodec(codec: VideoCodecType, options: (VideoCodec.Builder.() -> Unit)? = null): VideoCodec {
            val builder = VideoCodec.Builder(codec)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}