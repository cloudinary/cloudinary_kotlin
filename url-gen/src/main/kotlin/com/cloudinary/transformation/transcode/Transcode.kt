package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.Action

abstract class Transcode : Action {
    companion object {
        fun streamingProfile(profile: StreamingProfileType) = StreamingProfile(profile)
        fun streamingProfile(profile: String) = StreamingProfile(profile)
        fun keyframeInterval(seconds: Float) = KeyframeInterval(seconds)
        fun fps(fps: Float) = Fps.Builder().fixed(fps).build()
        fun fpsRange(from: Float) = Fps.Builder().min(from).build()
        fun fpsRange(from: Float, to: Float) = Fps.Builder().min(from).max(to).build()

        fun bitRate(bitrate: String, options: (Bitrate.Builder.() -> Unit)? = null): Bitrate {
            val builder = Bitrate.Builder(bitrate)
            options?.let { builder.it() }
            return builder.build()
        }


        fun audioFrequency(frequency: AudioFrequency) = AudioFrequencyTranscode(frequency)

        fun audioCodec(codec: AudioCodec) = AudioCodecTranscode(codec)
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