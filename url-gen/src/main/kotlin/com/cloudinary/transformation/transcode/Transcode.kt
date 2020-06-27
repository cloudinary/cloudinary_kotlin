package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.*

class Transcode(action: Action) : Action by action {
    companion object {
        fun audioCodec(codec: AudioCodec) = transcode(codec.cldAsAudioCodec())
        fun audioFrequency(frequency: AudioFrequency) = transcode(frequency.cldAsAudioFrequency())
        fun bitRate(bitrate: String) = transcode(bitrate.cldAsBitRate())
        fun videoCodec(codec: VideoCodec) = transcode(codec.cldAsVideoCodec())
        fun streamingProfile(profile: StreamingProfile) = transcode(profile.cldAsStreamingProfile())
        fun streamingProfile(profile: String) = transcode(profile.cldAsStreamingProfile())
        fun toAnimated(videoSampling: Int? = null, options: (AnimatedBuilder.() -> Unit)? = null): Transcode {
            val builder = AnimatedBuilder()
            videoSampling?.let { builder.videoSampling(it) }
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

class VideoCodec internal constructor(value: Any) : ParamValue(value) {
    companion object {
        fun vp8() = VideoCodecBuilder("vp8").build()
        fun vp9() = VideoCodecBuilder("vp9").build()
        fun prores() = VideoCodecBuilder("prores").build()
        fun h264(options: (VideoCodecBuilder.() -> Unit)? = null): VideoCodec {
            val builder = VideoCodecBuilder("h264")
            options?.let { builder.it() }
            return builder.build()
        }

        fun h265() = VideoCodecBuilder("h265").build()
        fun theora() = VideoCodecBuilder("theora").build()
        fun auto() = VideoCodecBuilder("auto").build()

    }
}

enum class StreamingProfile(private val value: String) {
    SP_4K("4k"),
    FULL_HD("full_hd"),
    HD("hd"),
    SD("sd"),
    FULL_HD_WIFI("full_hd_wifi"),
    FULL_HD_LEAN("full_hd_lean"),
    HD_LEAN("hd_lean");

    override fun toString() = value
}


class VideoCodecBuilder internal constructor(private val codec: String) {
    private var level: Any? = null
    private var profile: VideoCodecProfile? = null
    fun level(level: VideoCodecLevel) = apply { this.level = level }
    fun profile(profile: VideoCodecProfile) = apply { this.profile = profile }
    fun build() = VideoCodec(listOfNotNull(codec, profile, level))
}


enum class VideoCodecLevel(private val value: String) {
    VCL_30("3.0"),
    VCL_31("3.1"),
    VCL_40("4.0"),
    VCL_41("4.1"),
    VCL_42("4.2"),
    VCL_50("5.0"),
    VCL_51("5.1"),
    VCL_52("5.2");

    override fun toString(): String {
        return value
    }
}

enum class VideoCodecProfile(private val value: String) {
    BASELINE("baseline"),
    MAIN("main"),
    HIGH("high");

    override fun toString(): String {
        return value
    }
}

enum class AudioCodec(private val value: String) {
    NONE("none"),
    AAC("aac"),
    VORBIS("vorbis"),
    MP3("mp3");

    override fun toString(): String {
        return value
    }
}

enum class AudioFrequency(private val frequency: Int) {
    FREQ8000(8000),
    FREQ11025(11025),
    FREQ16000(16000),
    FREQ22050(22050),
    FREQ32000(32000),
    FREQ37800(37800),
    FREQ44056(44056),
    FREQ44100(44100),
    FREQ47250(47250),
    FREQ48000(48000),
    FREQ88200(88200),
    FREQ96000(96000),
    FREQ176400(176400),
    FREQ192000(192000);

    override fun toString(): String {
        return frequency.toString()
    }
}

internal fun transcode(vararg params: Param) = Transcode(ParamsAction(params.toList()))