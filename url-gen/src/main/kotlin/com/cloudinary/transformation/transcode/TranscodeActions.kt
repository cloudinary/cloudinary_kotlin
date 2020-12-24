package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.toComponentString
import com.cloudinary.util.cldRealPositive
import java.util.*

class StreamingProfile internal constructor(private val profile: Any) {
    override fun toString(): String {
        return "sp_$profile"
    }
}

// TODO no format enum?
class ToAnimated(
    private val animatedFormat: String,
    private val sampling: Any? = null,
    private val delay: Int? = null
) : Transcode() {

    override fun toString(): String {
        return listOfNotNull(
            delay?.let { Param("dl", it) },
            Param("f", animatedFormat),
            Param("fl", "animated"),
            if (animatedFormat.toLowerCase(Locale.ROOT) == "webp") Param("fl", "awebp") else null,
            sampling?.let { Param("vs", it) }
        ).toComponentString()
    }

    class Builder(private val animatedFormat: String) : TransformationComponentBuilder {
        private var sampling: Any? = null
        private var delay: Int? = null

        fun videoSampling(videoSampling: Int) = apply { this.sampling = videoSampling }
        fun videoSampling(videoSampling: String) = apply { this.sampling = videoSampling }
        fun delay(delay: Int) = apply { this.delay = delay }

        override fun build() = ToAnimated(animatedFormat, sampling, delay)
    }


}

class VideoCodec(
    private val codec: VideoCodecType,
    private val profile: VideoCodecProfile? = null,
    private val level: Any? = null
) : Transcode() {
    override fun toString(): String {
        return "vc_$codec".joinWithValues(profile, level)
    }

    class Builder internal constructor(private val codec: VideoCodecType) {
        private var profile: VideoCodecProfile? = null
        private var level: Any? = null

        fun profile(profile: VideoCodecProfile) = apply { this.profile = profile }
        fun level(level: VideoCodecLevel) = apply { this.level = level }

        fun build() = VideoCodec(codec, profile, level)
    }
}

class KeyframeInterval(private val seconds: Float) : Transcode() {
    init {
        seconds.cldRealPositive()
    }

    override fun toString(): String {
        return "ki_$seconds"
    }
}

class Bitrate(private val bitrate: String, private val constant: Boolean? = null) : Transcode() {
    override fun toString(): String {
        return "br_$bitrate".joinWithValues(if (constant == true) "constant" else null)
    }

    class Builder(private val bitrate: String) : TransformationComponentBuilder {
        private var constant: Boolean? = null

        fun constant(constant: Boolean = true) = apply { this.constant = constant }

        override fun build(): Bitrate {
            return Bitrate(bitrate, constant)
        }
    }
}

class Fps private constructor(private val fixed: Any?, private val min: Any?, private var max: Any?) : Transcode() {

    override fun toString(): String {
        return "fps_".run {
            if (fixed != null)
                "${this}$fixed"
            else
                "${this}${min}".joinWithValues(max ?: "", separator = "-")
        }
    }

    class Builder : TransformationComponentBuilder {
        private var fixed: Any? = null
        private var min: Any? = null
        private var max: Any? = null

        internal fun fixed(fps: Float) = apply { this.fixed = fps }
        fun min(min: Float) = apply { this.min = min }
        fun max(max: Float) = apply { this.max = max }
        fun min(min: Int) = apply { this.min = min }
        fun max(max: Int) = apply { this.max = max }
        fun min(min: Any) = apply { this.min = min }
        fun max(max: Any) = apply { this.max = max }

        override fun build() = Fps(fixed, min, max)
    }
}

class AudioFrequencyTranscode(private val audioFrequency: AudioFrequency) : Transcode() {
    override fun toString(): String {
        return "af_${audioFrequency}"
    }
}

class AudioCodecTranscode(private val codec: AudioCodec) : Transcode() {
    override fun toString(): String {
        return "ac_$codec"
    }
}

enum class AudioCodec(private val value: String) {
    NONE("none"),
    AAC("aac"),
    VORBIS("vorbis"),
    OPUS("opus"),
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

enum class StreamingProfileType(private val value: String) {
    SP_4K("4k"),
    FULL_HD("full_hd"),
    HD("hd"),
    SD("sd"),
    FULL_HD_WIFI("full_hd_wifi"),
    FULL_HD_LEAN("full_hd_lean"),
    HD_LEAN("hd_lean");

    override fun toString() = value
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
