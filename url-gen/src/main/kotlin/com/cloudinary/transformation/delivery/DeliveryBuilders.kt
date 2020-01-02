package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.*
import com.cloudinary.util.cldRanged
import com.cloudinary.util.cldRealPositive


class StreamingProfile private constructor(profile: String) : Delivery(
    Param(
        "streaming_profile",
        "sp",
        ParamValue(profile)
    )
) {
    class Builder(private val profile: String) : TransformationComponentBuilder {
        override fun build() = StreamingProfile(profile)
    }
}

class VideoCodec private constructor(codec: VideoCodecType, profile: VideoCodecProfile?, level: Any?) :
    Delivery(
        Param(
            "video_codec",
            "vc",
            ParamValue(listOfNotNull(codec, profile, level))
        )
    ) {
    class Builder(private val codec: VideoCodecType) : TransformationComponentBuilder {
        private var level: Any? = null
        private var profile: VideoCodecProfile? = null
        fun level(level: Float) = apply { this.level = level }
        fun profile(profile: VideoCodecProfile) = apply { this.profile = profile }
        override fun build() = VideoCodec(codec, profile, level)
    }
}

class Fps private constructor(fixed: Any?, min: Any?, max: Any?) : Delivery(
    Param(
        "fps",
        "fps",
        ParamValue(
            (if (fixed != null) listOf(fixed) else listOfNotNull(min, max ?: "")).cldAsParamValueContent(),
            "-"
        )
    )
) {
    class Builder : TransformationComponentBuilder {
        private var fixed: Any? = null
        private var min: Any? = null
        private var max: Any? = null

        internal fun fixed(fps: Int) = apply { this.fixed = fps }
        fun min(min: Int) = apply { this.min = min }
        fun max(max: Int) = apply { this.max = max }
        fun min(min: Float) = apply { this.min = min }
        fun max(max: Float) = apply { this.max = max }
        fun min(min: Any) = apply { this.min = min }
        fun max(max: Any) = apply { this.max = max }

        override fun build() = Fps(fixed, min, max)
    }
}

class AudioCodec private constructor(codec: AudioCodecType) :
    Delivery(
        Param(
            "audio_codec",
            "ac",
            ParamValue(codec)
        )
    ) {
    class Builder(private val codec: AudioCodecType) : TransformationComponentBuilder {
        override fun build() = AudioCodec(codec)
    }
}

class AudioFrequency private constructor(frequency: AudioFrequencyType) :
    Delivery(
        Param(
            "audio_frequency",
            "af",
            ParamValue(frequency)
        )
    ) {
    class Builder(private val frequency: AudioFrequencyType) : TransformationComponentBuilder {
        override fun build() = AudioFrequency(frequency)
    }
}

class Format private constructor(format: String) :
    Delivery(
        Param(
            "fetch_format",
            "f",
            ParamValue(format)
        )
    ) {

    class Builder(private val format: String) : TransformationComponentBuilder {
        override fun build() = Format(format)
    }
}

class Quality private constructor(value: ParamValue, flag: FlagKey? = null) :
    Delivery(
        listOfNotNull(
            Param("quality", "q", value),
            flag?.asParam()
        ).cldToActionMap()
    ) {

    class Builder internal constructor(private val level: Any? = null, private val type: QualityType? = null) :
        TransformationComponentBuilder {
        constructor(level: Int) : this(level, null)
        constructor(type: QualityType) : this(null, type)

        private var maxQuantization: Any? = null // Int
        private var chromaSubSampling: ChromaSubSampling? = null
        private var preset: AutoQuality? = null
        private var anyFormat: Boolean? = null

//        fun level(level: Int) = apply { this.level = level.cldRanged(1, 100) }
//        fun setLevel(level: Any) = apply { this.level = level.cldRanged(1, 100) }

        fun maxQuantization(maxQuantization: Any) =
            apply { this.maxQuantization = maxQuantization.cldRanged(1, 100) }

        fun maxQuantization(maxQuantization: Int) =
            apply { this.maxQuantization = maxQuantization.cldRanged(1, 100) }


        fun chromaSubSampling(chromaSubSampling: ChromaSubSampling) =
            apply { this.chromaSubSampling = chromaSubSampling }

        fun preset(preset: AutoQuality) = apply { this.preset = preset }

        fun anyFormat(anyFormat: Boolean) = apply { this.anyFormat = anyFormat }

        override fun build() = Quality(
            ParamValue(
                listOfNotNull(
                    type,
                    level,
                    maxQuantization?.let { NamedValue("qmax", maxQuantization!!, "_") },
                    chromaSubSampling,
                    preset
                )
            ), if (anyFormat == true) FlagKey.ANY_FORMAT() else null
        )
    }
}

class DefaultImage private constructor(publicId: String) :
    Delivery(
        Param(
            "default_image",
            "d",
            ParamValue(publicId)
        )
    ) {
    class Builder(private val publicId: String) : TransformationComponentBuilder {
        override fun build() = DefaultImage(publicId)
    }
}

class ColorSpace private constructor(colorSpace: ColorSpaceType) :
    Delivery(
        Param(
            "color_space",
            "cs",
            ParamValue(colorSpace)
        )
    ) {
    class Builder(private val colorSpace: ColorSpaceType) : TransformationComponentBuilder {
        override fun build() = ColorSpace(colorSpace)
    }
}

class KeyframeInterval private constructor(seconds: Float) :
    Delivery(
        Param(
            "keyframe_interval",
            "ki",
            ParamValue(seconds.cldRealPositive())
        )
    ) {
    class Builder(private val seconds: Float) : TransformationComponentBuilder {
        override fun build() = KeyframeInterval(seconds)
    }
}