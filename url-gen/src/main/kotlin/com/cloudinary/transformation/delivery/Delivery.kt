package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRealPositive
import com.cloudinary.util.cldToString

class Delivery(private val action: Action) : Action by action {


    companion object {
        fun streamingProfile(profile: String) = delivery(profile.cldAsStreamingProfile())

        fun videoCodec(codec: VideoCodec, options: (VideoCodecBuilder.() -> Unit)? = null): Delivery {
            val builder = VideoCodecBuilder(codec)
            options?.let { builder.it() }
            return builder.build()
        }

        fun fps(fps: Float) = FpsBuilder().fixed(fps).build()
        fun fps(fps: Int) = FpsBuilder().fixed(fps).build()
        fun fps(options: (FpsBuilder.() -> Unit)? = null): Delivery {
            val builder = FpsBuilder()
            options?.let { builder.options() }
            return builder.build()
        }

        fun audioCodec(codec: AudioCodec) = delivery(codec.cldAsAudioCodec())

        fun audioFrequency(frequency: AudioFrequency) = delivery(frequency.cldAsAudioFrequency())

        fun defaultImage(publicId: String) = delivery(publicId.cldAsDefaultImage())

        fun colorSpace(colorSpace: ColorSpace) = delivery(colorSpace.cldAsColorSpace())

        fun keyframeInterval(seconds: Float) = delivery((seconds.cldRealPositive().cldAsKeyframeInterval()))
    }
}

enum class VideoCodec(private val value: String) {
    VP8("vp8"),
    VP9("vp9"),
    PRORES("prores"),
    H264("h264"),
    H265("h265"),
    THEORA("theora"),
    AUTO("auto");

    override fun toString(): String {
        return value
    }
}

enum class VideoCodecProfile(private val value: String) {
    VCP_BASELINE("baseline"),
    VCP_MAIN("main"),
    VCP_HIGH("high");

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
    HZ_8000(8000),
    HZ_11025(11025),
    HZ_16000(16000),
    HZ_22050(22050),
    HZ_32000(32000),
    HZ_37800(37800),
    HZ_44056(44056),
    HZ_44100(44100),
    HZ_47250(47250),
    HZ_48000(48000),
    HZ_88200(88200),
    HZ_96000(96000),
    HZ_176400(176400),
    HZ_192000(192000);

    override fun toString(): String {
        return frequency.cldToString()
    }
}

sealed class ColorSpace(value: Any) : ParamValue(value) {
    constructor(value: String) : this(ParamValue(value))

    object SRGB : ColorSpace("srgb")
    object TINYSRGB : ColorSpace("tinysrgb")
    object CMYK : ColorSpace("cmyk")
    object NO_CMYK : ColorSpace("no_cmyk")
    object KEEP_CMYK : ColorSpace("keep_cmyk")
    class CsIcc(publicId: String) : ColorSpace(
        ParamValue(listOfNotNull("icc", publicId))
    )
}

internal fun delivery(vararg params: Param) = Delivery(CParamsAction(params.toList()))