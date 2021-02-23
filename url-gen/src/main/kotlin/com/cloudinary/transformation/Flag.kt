package com.cloudinary.transformation

import com.cloudinary.transformation.delivery.ProgressiveMode

class FlagAction(private val value: Any) : Action {
    override fun toString() = if (value is Flag) value.toString() else "fl_$value"
}

class Flag(vararg values: Any?) {

    val values: List<Any> = values.toList().filterNotNull()

    companion object {
        fun anyFormat() = Flag("any_format")
        fun attachment(name: String? = null) = Flag("attachment", name)
        fun aPng() = Flag("apng")
        fun animatedWebP() = Flag("awebp")
        fun clip() = Flag("clip")
        fun clipEvenOdd() = Flag("clip_evenodd")
        fun cutter() = Flag("cutter")
        fun forceStrip() = Flag("force_strip")
        fun forceIcc() = Flag("force_icc")
        fun getInfo() = Flag("getinfo")
        fun ignoreAspectRatio() = Flag("ignore_aspect_ratio")
        fun immutableCache() = Flag("immutable_cache")
        fun keepAttribution() = Flag("keep_attribution")
        fun keepIptc() = Flag("keep_iptc")
        fun layerApply() = Flag("layer_apply")
        fun lossy() = Flag("lossy")
        fun noOverflow() = Flag("no_overflow")
        fun preserveTransparency() = Flag("preserve_transparency")
        fun png8() = Flag("png8")
        fun png24() = Flag("png24")
        fun png32() = Flag("png32")
        fun progressive(mode: ProgressiveMode? = null) = Flag("progressive", mode)
        fun rasterize() = Flag("rasterize")
        fun regionRelative() = Flag("region_relative")
        fun relative() = Flag("relative")
        fun replaceImage() = Flag("replace_image")
        fun sanitize() = Flag("sanitize")
        fun stripProfile() = Flag("strip_profile")
        fun textNoTrim() = Flag("text_no_trim")
        fun textDisallowOverflow() = Flag("text_disallow_overflow")
        fun tiff8Lzw() = Flag("tiff8_lzw")
        fun tiled() = Flag("tiled")
        fun animated() = Flag("animated")
        fun streamingAttachment(name: String) = Flag("streaming_attachment", name)
        fun hlsV3() = Flag("hlsv3")
        fun keepDar() = Flag("keep_dar")
        fun noStream() = Flag("no_stream")
        fun mono() = Flag("mono")
        fun splice() = Flag("splice")
        fun truncateTS() = Flag("truncate_ts")
        fun waveform() = Flag("waveform")
    }

    override fun toString(): String {
        return "fl_${values.joinToString(":")}"
    }
}