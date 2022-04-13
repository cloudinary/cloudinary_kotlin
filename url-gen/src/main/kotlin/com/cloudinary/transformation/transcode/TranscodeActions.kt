package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.*
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.util.cldRealPositive
import java.util.*

class StreamingProfileAction internal constructor(private val profile: Any) : Transcode() {
    override fun toString(): String {
        return "sp_$profile"
    }
}

// TODO no format enum?
class ToAnimated(
    private val animatedFormat: Any,
    private val sampling: Any? = null,
    private val delay: Int? = null
) : Transcode() {

    override fun toString(): String {
        return listOfNotNull(
            delay?.let { Param("dl", it) },
            Param("f", animatedFormat),
            Param("fl", "animated"),
            if (animatedFormat.toString().toLowerCase(Locale.ROOT) == "webp") Param("fl", "awebp") else null,
            sampling?.let { Param("vs", it) }
        ).toComponentString()
    }

    class Builder internal constructor(private val animatedFormat: Any) : TransformationComponentBuilder {
        constructor(animatedFormat: AnimatedFormat) : this(animatedFormat as Any)
        constructor(animatedFormat: String) : this(animatedFormat as Any)

        private var sampling: Any? = null
        private var delay: Int? = null

        fun sampling(videoSampling: Int) = apply { this.sampling = videoSampling }
        fun sampling(videoSampling: String) = apply { this.sampling = videoSampling }
        fun delay(delay: Int) = apply { this.delay = delay }

        override fun build() = ToAnimated(animatedFormat, sampling, delay)
    }


}

class VideoCodecAction(private val codec: VideoCodec) : Transcode() {
    override fun toString(): String {
        return "vc_$codec"
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

class Bitrate(private val bitrate: Any, private val constant: Boolean? = null) : Transcode() {
    override fun toString(): String {
        return "br_$bitrate".joinWithValues(if (constant == true) "constant" else null)
    }

    class Builder(private val bitrate: Any) : TransformationComponentBuilder {
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

class AudioFrequencyTranscode(private val audioFrequency: Any) : Transcode() {
    override fun toString(): String {
        return "af_${audioFrequency}"
    }
}

class AudioCodecTranscode(private val codec: AudioCodec) : Transcode() {
    override fun toString(): String {
        return "ac_$codec"
    }
}

class AudioCodec private constructor(private val value: String) {
    companion object {
        private val none = AudioCodec("none")
        fun none() = none
        private val aac = AudioCodec("aac")
        fun aac() = aac
        private val vorbis = AudioCodec("vorbis")
        fun vorbis() = vorbis
        private val opus = AudioCodec("opus")
        fun opus() = opus
        private val mp3 = AudioCodec("mp3")
        fun mp3() = mp3
    }

    override fun toString(): String {
        return value
    }
}

class AudioFrequency private constructor(private val frequency: Any) {
    companion object {
        private val freq8000 = AudioFrequency(8000)
        fun freq8000() = freq8000
        private val freq11025 = AudioFrequency(11025)
        fun freq11025() = freq11025
        private val freq16000 = AudioFrequency(16000)
        fun freq16000() = freq16000
        private val freq22050 = AudioFrequency(22050)
        fun freq22050() = freq22050
        private val freq32000 = AudioFrequency(32000)
        fun freq32000() = freq32000
        private val freq37800 = AudioFrequency(37800)
        fun freq37800() = freq37800
        private val freq44056 = AudioFrequency(44056)
        fun freq44056() = freq44056
        private val freq44100 = AudioFrequency(44100)
        fun freq44100() = freq44100
        private val freq47250 = AudioFrequency(47250)
        fun freq47250() = freq47250
        private val freq48000 = AudioFrequency(48000)
        fun freq48000() = freq48000
        private val freq88200 = AudioFrequency(88200)
        fun freq88200() = freq88200
        private val freq96000 = AudioFrequency(96000)
        fun freq96000() = freq96000
        private val freq176400 = AudioFrequency(176400)
        fun freq176400() = freq176400
        private val freq192000 = AudioFrequency(192000)
        fun freq192000() = freq192000
        private val original = AudioFrequency("iaf")
        fun original() = original
    }

    override fun toString(): String {
        return frequency.toString()
    }
}

class StreamingProfile private constructor(private val value: String) {
    companion object {
        private val sp4K = StreamingProfile("4k")
        fun sp4K() = sp4K
        private val fullHd = StreamingProfile("full_hd")
        fun fullHd() = fullHd
        private val hd = StreamingProfile("hd")
        fun hd() = hd
        private val sd = StreamingProfile("sd")
        fun sd() = sd
        private val fullHdWifi = StreamingProfile("full_hd_wifi")
        fun fullHdWifi() = fullHdWifi
        private val fullHdLean = StreamingProfile("full_hd_lean")
        fun fullHdLean() = fullHdLean
        private val hdLean = StreamingProfile("hd_lean")
        fun hdLean() = hdLean
    }

    override fun toString() = value
}

class VideoCodecLevel private constructor(private val value: String) {
    companion object {
        private val vcl30 = VideoCodecLevel("3.0")
        fun vcl30() = vcl30
        private val vcl31 = VideoCodecLevel("3.1")
        fun vcl31() = vcl31
        private val vcl40 = VideoCodecLevel("4.0")
        fun vcl40() = vcl40
        private val vcl41 = VideoCodecLevel("4.1")
        fun vcl41() = vcl41
        private val vcl42 = VideoCodecLevel("4.2")
        fun vcl42() = vcl42
        private val vcl50 = VideoCodecLevel("5.0")
        fun vcl50() = vcl50
        private val vcl51 = VideoCodecLevel("5.1")
        fun vcl51() = vcl51
        private val vcl52 = VideoCodecLevel("5.2")
        fun vcl52() = vcl52
    }

    override fun toString(): String {
        return value
    }
}

class VideoCodecProfile private constructor(private val value: String) {
    companion object {
        private val baseline = VideoCodecProfile("baseline")
        fun baseline() = baseline
        private val main = VideoCodecProfile("main")
        fun main() = main
        private val high = VideoCodecProfile("high")
        fun high() = high
    }

    override fun toString(): String {
        return value
    }
}

open class VideoCodec(protected val codec: Any) {
    companion object {
        private val vp8 = VideoCodec("vp8")
        fun vp8() = vp8
        private val vp9 = VideoCodec("vp9")
        fun vp9() = vp9
        private val prores = VideoCodec("prores")
        fun prores() = prores
        fun h264(options: (H264Codec.Builder.() -> Unit)? = null): H264Codec {
            val builder = H264Codec.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        private val h265 = VideoCodec("h265")
        fun h265() = h265
        private val theora = VideoCodec("theora")
        fun theora() = theora
        private val auto = VideoCodec("auto")
        fun auto() = auto
    }

    override fun toString(): String {
        return codec.toString()
    }
}

class H264Codec private constructor(private val profile: VideoCodecProfile? = null, private val level: Any? = null) :
    VideoCodec("h264") {
    override fun toString(): String {
        return codec.joinWithValues(profile, level)
    }

    @TransformationDsl
    class Builder {
        private var profile: VideoCodecProfile? = null
        private var level: Any? = null

        fun profile(profile: VideoCodecProfile) = apply { this.profile = profile }
        fun level(level: VideoCodecLevel) = apply { this.level = level }
        fun level(level: Float) = apply { this.level = level }
        fun level(level: Expression) = apply { this.level = level }

        fun build() = H264Codec(profile, level)
    }
}

class AnimatedFormat private constructor(private val value: String) {
    companion object {
        private val auto = AnimatedFormat("auto")
        fun auto() = auto
        private val gif = AnimatedFormat("gif")
        fun gif() = gif
        private val webp = AnimatedFormat("webp")
        fun webp() = webp
        private val png = AnimatedFormat("png")
        fun png() = png
    }

    override fun toString() = value
}