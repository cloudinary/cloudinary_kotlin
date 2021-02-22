package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.Action

abstract class Transcode : Action {
    companion object {
        fun streamingProfile(profile: StreamingProfile) = StreamingProfileAction(profile)
        fun streamingProfile(profile: String) = StreamingProfileAction(profile)
        fun keyframeInterval(seconds: Float) = KeyframeInterval(seconds)
        fun fps(fps: Float) = Fps.Builder().fixed(fps).build()
        fun fpsRange(from: Float) = Fps.Builder().min(from).build()
        fun fpsRange(from: Float, to: Float) = Fps.Builder().min(from).max(to).build()
        fun fpsRange(from: Int, to: Int) = Fps.Builder().min(from).max(to).build()

        private fun bitRate(bitrate: Any, options: (Bitrate.Builder.() -> Unit)? = null): Bitrate {
            val builder = Bitrate.Builder(bitrate)
            options?.let { builder.it() }
            return builder.build()
        }

        fun bitRate(bitrate: Int, options: (Bitrate.Builder.() -> Unit)? = null) = bitRate(bitrate as Any, options)
        fun bitRate(bitrate: String, options: (Bitrate.Builder.() -> Unit)? = null) = bitRate(bitrate as Any, options)


        fun audioFrequency(frequency: AudioFrequency) = AudioFrequencyTranscode(frequency)
        fun audioFrequency(frequency: String) = AudioFrequencyTranscode(frequency)
        fun audioFrequency(frequency: Int) = AudioFrequencyTranscode(frequency)

        fun audioCodec(codec: AudioCodec) = AudioCodecTranscode(codec)
        fun toAnimated(animatedFormat: String, options: (ToAnimated.Builder.() -> Unit)? = null) =
            toAnimated(animatedFormat as Any, options)

        fun toAnimated(animatedFormat: AnimatedFormat, options: (ToAnimated.Builder.() -> Unit)? = null) =
            toAnimated(animatedFormat as Any, options)

        private fun toAnimated(animatedFormat: Any, options: (ToAnimated.Builder.() -> Unit)? = null): Transcode {
            val builder = ToAnimated.Builder(animatedFormat)
            options?.let { builder.it() }
            return builder.build()
        }

        fun videoCodec(codec: VideoCodec) = VideoCodecAction(codec)
    }
}