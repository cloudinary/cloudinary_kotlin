package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.Action

/**
 * Defines how to transcode a video to another format
 *
 * **Learn More**: [Transcoding video to other formats](https://cloudinary.com/documentation/video_manipulation_and_delivery#transcoding_video_to_other_formats)
 */
abstract class Transcode : Action {
    /**
     *
     */
    companion object {
        /**
         * Sets the streaming profile to apply to an HLS or MPEG-DASH adaptive bitrate streaming video.
         * @param profile The profile to use.
         */
        fun streamingProfile(profile: StreamingProfile) = StreamingProfileAction(profile)
        /**
         * Sets the streaming profile to apply to an HLS or MPEG-DASH adaptive bitrate streaming video.
         * @param profile The profile to use.
         */
        fun streamingProfile(profile: String) = StreamingProfileAction(profile)
        /**
         * Sets the keyframe interval of the delivered video.
         * @param seconds The interval in seconds.
         */
        fun keyframeInterval(seconds: Float) = KeyframeInterval(seconds)
        /**
         * Controls the FPS (Frames Per Second) of a video to ensure that the video (even when optimized) is delivered with an expected FPS level (helps with sync to audio). Can also be specified as a range.
         * @param fps  The frame rate.
         */
        fun fps(fps: Float) = Fps.Builder().fixed(fps).build()
        /**
         * Controls the range of acceptable FPS (Frames Per Second) to ensure that video (even when optimized) is delivered with an expected FPS level (helps with sync to audio).
         * @param from The minimum frame rate.
         */
        fun fpsRange(from: Float) = Fps.Builder().min(from).build()
        /**
         * Controls the range of acceptable FPS (Frames Per Second) to ensure that video (even when optimized) is delivered with an expected FPS level (helps with sync to audio).
         * @param from The minimum frame rate.
         * @param to The maximum frame rate.
         */
        fun fpsRange(from: Float, to: Float) = Fps.Builder().min(from).max(to).build()
        /**
         * Controls the range of acceptable FPS (Frames Per Second) to ensure that video (even when optimized) is delivered with an expected FPS level (helps with sync to audio).
         * @param from  The minimum frame rate.
         * @param to The maximum frame rate.
         */
        fun fpsRange(from: Int, to: Int) = Fps.Builder().min(from).max(to).build()

        private fun bitRate(bitrate: Any, options: (Bitrate.Builder.() -> Unit)? = null): Bitrate {
            val builder = Bitrate.Builder(bitrate)
            options?.let { builder.it() }
            return builder.build()
        }
        /**
         * Controls the video bitrate.
         * Supported codecs: h264, h265 (MPEG-4); vp8, vp9 (WebM).
         * @param bitrate The number of bits used to represent the video data per second. By default the video uses a variable bitrate (VBR), with this value indicating the maximum bitrate. The value can be an integer e.g. 120000, or a string supporting "k" and "m" (kilobits and megabits respectively) e.g. 250k or 2m.
         *
         */
        fun bitRate(bitrate: Int, options: (Bitrate.Builder.() -> Unit)? = null) = bitRate(bitrate as Any, options)
        /**
         * Controls the video bitrate.
         * Supported codecs: h264, h265 (MPEG-4); vp8, vp9 (WebM).
         * @param bitrate The number of bits used to represent the video data per second. By default the video uses a variable bitrate (VBR), with this value indicating the maximum bitrate. The value can be an integer e.g. 120000, or a string supporting "k" and "m" (kilobits and megabits respectively) e.g. 250k or 2m.
         *
         */
        fun bitRate(bitrate: String, options: (Bitrate.Builder.() -> Unit)? = null) = bitRate(bitrate as Any, options)

        /**
         * Sets the audio sample frequency.
         * @param frequency The audio frequency.
         */
        fun audioFrequency(frequency: AudioFrequency) = AudioFrequencyTranscode(frequency)
        /**
         * Sets the audio sample frequency.
         * @param frequency The audio frequency.
         */
        fun audioFrequency(frequency: String) = AudioFrequencyTranscode(frequency)
        /**
         * Sets the audio sample frequency.
         * @param frequency The audio frequency.
         */
        fun audioFrequency(frequency: Int) = AudioFrequencyTranscode(frequency)
        /**
         * Sets the audio codec or removes the audio channel.
         * @param codec The audio codec or "none".
         */
        fun audioCodec(codec: AudioCodec) = AudioCodecTranscode(codec)
        /**
         * Converts a video to animated image.
         * @param format The animated image format.
         */
        fun toAnimated(options: (ToAnimated.Builder.() -> Unit)? = null): Transcode {
            val builder = ToAnimated.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
        /**
         * Controls the video codec.
         * @param codec The video codec.
         */
        fun videoCodec(codec: VideoCodec) = VideoCodecAction(codec)
    }
}