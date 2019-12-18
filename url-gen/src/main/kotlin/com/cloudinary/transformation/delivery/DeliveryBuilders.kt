package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.cldAsNonNullSimpleValues
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

class VideoCodec private constructor(codec: VideoCodecType, profile: String?, level: Any?) :
    Delivery(
        Param(
            "video_codec",
            "vc",
            ParamValue(listOfNotNull(codec, profile, level))
        )
    ) {
    class Builder(private val codec: VideoCodecType) : TransformationComponentBuilder {
        private var level: Any? = null
        private var profile: String? = null
        fun setLevel(level: Float) = apply { this.level = level }
        fun setProfile(profile: String) = apply { this.profile = profile }
        override fun build() = VideoCodec(codec, profile, level)
    }
}

class Fps private constructor(fixed: Any?, min: Any?, max: Any?) : Delivery(
    Param(
        "fps",
        "fps",
        ParamValue(
            (if (fixed != null) listOf(fixed) else listOfNotNull(min, max ?: "")).cldAsNonNullSimpleValues(),
            "-"
        )
    )
) {
    class Builder : TransformationComponentBuilder {
        private var fixed: Any? = null
        private var min: Any? = null
        private var max: Any? = null

        fun setFixed(fps: Int) = apply { this.fixed = fps }
        fun setMin(min: Int) = apply { this.min = min }
        fun setMax(max: Int) = apply { this.max = max }

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

class Quality private constructor(quality: QualityType) :
    Delivery(
        Param(
            "quality",
            "q",
            quality
        )
    ) {

    class Builder(private val quality: QualityType) : TransformationComponentBuilder {
        override fun build() = Quality(quality)
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