package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.ParamsAction
import com.cloudinary.util.cldToString

open class Delivery(params: Map<String, Param>) : ParamsAction<Delivery>(params) {
    constructor(param: Param) : this(mapOf(Pair(param.key, param)))

    override fun create(params: Map<String, Param>) =
        Delivery(params)

    companion object {
        fun streamingProfile(profile: String) = StreamingProfile.Builder(profile).build()

        fun videoCodec(
            codec: VideoCodecType, videoCodec: (VideoCodec.Builder.() -> Unit)? = null
        ): VideoCodec {
            val builder = VideoCodec.Builder(codec)
            videoCodec?.let { builder.it() }
            return builder.build()
        }

        fun fps(fps: Int) = Fps.Builder().fixed(fps).build()

        fun fps(min: Any, max: Any?): Fps {
            val builder = Fps.Builder().min(min)
            max?.let { builder.max(max) }
            return builder.build()
        }

        fun fps(min: Int, max: Int?) = fps(min as Any, max)
        fun fps(min: Float, max: Float?) = fps(min as Any, max)

        fun audioCodec(codec: AudioCodecType) = AudioCodec.Builder(codec).build()

        fun audioFrequency(frequency: AudioFrequencyType) = AudioFrequency.Builder(frequency).build()

        fun format(format: String) = Format.Builder(format).build()

        fun quality(type: QualityType, quality: (Quality.Builder.() -> Unit)? = null): Quality {
            val builder = Quality.Builder(type)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun quality(level: Int, quality: (Quality.Builder.() -> Unit)? = null): Quality {
            val builder = Quality.Builder(level)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun defaultImage(publicId: String) = DefaultImage.Builder(publicId).build()

        fun colorSpace(colorSpace: ColorSpaceType) = ColorSpace.Builder(colorSpace).build()

        fun keyframeInterval(seconds: Float) = KeyframeInterval.Builder(seconds).build()
    }
}

enum class QualityType(private val value: String) {
    AUTO("auto"),
    JPEG_MINI("jpegmini");

    override fun toString() = value
}

enum class ChromaSubSampling(private val value: String) {
    C_444("444"),
    C_420("420");

    override fun toString() = value
}

enum class AutoQuality(private val value: String) {
    BEST("best"),
    GOOD("good"),
    ECO("eco"),
    LOW("low");

    override fun toString() = value
}

enum class VideoCodecType(private val value: String) {
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

enum class AudioCodecType(private val value: String) {
    NONE("none"),
    AAC("aac"),
    VORBIS("vorbis"),
    MP3("mp3");

    override fun toString(): String {
        return value
    }
}

enum class AudioFrequencyType(private val frequency: Int) {
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

sealed class ColorSpaceType(value: Any) : ParamValue(value) {
    constructor(value: String) : this(ParamValue(value))

    object Srgb : ColorSpaceType("srgb")
    object TinySrgb : ColorSpaceType("tinysrgb")
    object CMYK : ColorSpaceType("cmyk")
    object NoCmyk : ColorSpaceType("no_cmyk")
    object KeepCmyk : ColorSpaceType("keep_cmyk")
    class CsIcc(publicId: String) : ColorSpaceType(
        ParamValue(listOfNotNull("icc", publicId))
    )
}