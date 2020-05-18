package com.cloudinary.transformation

import com.cloudinary.util.cldRanged
import com.cloudinary.util.cldToString

private fun widthParam(w: Any) = Param("width", "w", ParamValue(w))
private fun heightParam(h: Any) = Param("height", "h", ParamValue(h))
private fun aspectRatioParam(ar: Any) = Param("aspect_ratio", "ar", ParamValue(ar))
private fun dprParam(dpr: Any) = Param("dpr", "dpr", ParamValue(dpr))
private fun xParam(x: Any?) = x?.run { Param("x", "x", ParamValue(x)) }
private fun yParam(y: Any?) = y?.run { Param("y", "y", ParamValue(y)) }
private fun zoomParam(zoom: Any) = Param("zoom", "z", ParamValue(zoom))
private fun cropParam(cropMode: Any) = Param("crop", "c", ParamValue(cropMode))
private fun gravityParam(cropMode: Any) = Param("gravity", "g", ParamValue(cropMode))
private fun backgroundParam(value: Any) = Param("background", "b", ParamValue(value))
private fun streamingProfile(profile: Any) = Param("streaming_profile", "sp", ParamValue(profile))
private fun videoCodec(codec: Any) = Param("video_codec", "vc", ParamValue(codec))
private fun effectParam(effect: Any) = Param("effect", "e", ParamValue(effect))
private fun fpsParam(fps: Any) = Param("fps", "fps", ParamValue(fps))
private fun audioCodecParam(codec: Any) = Param("audio_codec", "ac", ParamValue(codec))
private fun audioFrequencyParam(frequency: Any) = Param("audio_frequency", "af", ParamValue(frequency))
private fun defaultImageParam(publicId: Any) = Param("default_image", "d", ParamValue(publicId))
private fun colorSpaceParam(colorSpace: Any) = Param("color_space", "cs", ParamValue(colorSpace))
private fun keyframeIntervalParam(seconds: Any) = Param("keyframe_interval", "ki", ParamValue(seconds))
private fun startOffsetParam(seconds: Any) = Param("start_offset", "so", ParamValue(seconds))
private fun endOffsetParam(seconds: Any) = Param("end_offset", "eo", ParamValue(seconds))
private fun durationParam(seconds: Any) = Param("duration", "du", ParamValue(seconds))
private fun colorParam(color: Any) = Param("color", "co", color)
private fun flagParam(flag: Any) = FlagsParam(flag)

internal fun Any.cldAsWidth() = widthParam(this)
internal fun Any.cldAsHeight() = heightParam(this)
internal fun Any.cldAsAspectRatio() = aspectRatioParam(this)
internal fun Any.cldAsDpr() = dprParam(this)
internal fun Any.cldAsX() = xParam(this)
internal fun Any.cldAsY() = yParam(this)
internal fun Any.cldAsZoom() = zoomParam(this)
internal fun Any.cldAsCrop() = cropParam(this)
internal fun Any.cldAsPage() = pageParam(this)
internal fun Any.cldAsGravity() = gravityParam(this)
internal fun Any.cldAsEffect() = effectParam(this)
internal fun Any.cldAsVideoCodec() = videoCodec(this)
internal fun Any.cldAsBackground() = backgroundParam(this)
internal fun Any.cldAsStreamingProfile() = streamingProfile(this)
internal fun Any.cldAsFps() = fpsParam(this)
internal fun Any.cldAsAudioCodec() = audioCodecParam(this)
internal fun Any.cldAsAudioFrequency() = audioFrequencyParam(this)
internal fun Any.cldAsDefaultImage() = defaultImageParam(this)
internal fun Any.cldAsColorSpace() = colorSpaceParam(this)
internal fun Any.cldAsKeyframeInterval() = keyframeIntervalParam(this)
internal fun Any.cldAsStartOffset() = startOffsetParam(this)
internal fun Any.cldAsEndOffset() = endOffsetParam(this)
internal fun Any.cldAsDuration() = durationParam(this)
internal fun Any.cldAsColor() = colorParam(this)
internal fun Any.cldAsFlag() = flagParam(this)

internal fun Any.asNamedValue(
    name: String,
    separator: String = DEFAULT_VALUES_SEPARATOR
) =
    NamedValue(name, this, separator)

internal fun Param.asAction() = ParamsAction(this)
internal fun List<Param>.asAction() = ParamsAction(this)
internal fun pageParam(page: Any) = Param("page", "pg", ParamValue(page))


class ColorParam(color: Color) : Param("color", "co", color)

internal class Range(private val from: Int? = null, private val to: Int? = null) {
    override fun toString() =
        if (from == to) from!!.cldToString() else "${(from?.cldToString() ?: "")}-${(to?.cldToString()
            ?: "")}"

    companion object {
        fun from(from: Int) = Range(from = from)
        fun to(to: Int) = Range(to = to)
        fun fromTo(from: Int, to: Int) = Range(from, to)
        fun single(num: Int) = Range(num, num)
    }
}

class EagerTransformation(val transformation: Transformation, val format: String? = null)

class Format(private val value: String) {
    companion object {
        fun auto() = Format("auto")
        fun jpg() = Format("jpg")
        fun webp() = Format("webp")
        fun jp2() = Format("jp2")
        fun png() = Format("png")
    }

    override fun toString() = value
    fun asAction() = ParamsAction(Param("fetch_format", "f", value))
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

// TODO this is not consistent with anything.
// TODO simplify
class Quality(private val action: Action) : Action by action {

//    ParamsAction(
//        listOfNotNull(
//            Param("quality", "q", value),
//            flag?.cldAsFlag()
//        ).cldToActionMap()
//    ) {

    companion object {
        fun auto(quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(QualityType.AUTO)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun jpegmini(quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(QualityType.JPEG_MINI)
            quality?.let { builder.it() }
            return builder.build()
        }

        fun fixed(level: Int, quality: (Builder.() -> Unit)? = null): Quality {
            val builder = Builder(level = level)
            quality?.let { builder.it() }
            return builder.build()
        }
    }

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
            ParamsAction(
                Param(
                    "quality", "q",
                    ParamValue(
                        listOfNotNull(
                            type,
                            level,
                            maxQuantization?.let { NamedValue("qmax", maxQuantization!!, "_") },
                            chromaSubSampling,
                            preset
                        )
                    )
                ), if (anyFormat == true) Flag.AnyFormat().cldAsFlag() else null
            )
        )
    }
}

