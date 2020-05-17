package com.cloudinary.transformation

open class FlagsParam(flag: Any) : Param("flags", "fl", ParamValue(flag)) {
    constructor(flag: Flag) : this(flag as Any)

    // flag param is allowed to appear several time per action, so the hashing key includes the flag value itself.
    private val _hashKey: String = "fl_$flag"

    override val hashKey = _hashKey
}

// Make these objects instead of classes
sealed class Flag(vararg items: Any?) : ParamValue(items.toList().filterNotNull()) {
    class AnyFormat : Flag("any_format")
    class Attachment : Flag("attachment")
    class APng : Flag("apng")
    class AWebp : Flag("awebp")
    class Clip : Flag("clip")
    class ClipEvenOdd : Flag("clip_evenodd")
    class Cutter : Flag("cutter")
    class ForceStrip : Flag("force_strip")
    class ForceIcc : Flag("force_icc")
    class GetInfo : Flag("getinfo")
    class IgnoreAspectRatio : Flag("ignore_aspect_ratio")
    class ImmutableCache : Flag("immutable_cache")
    class KeepAttribution : Flag("keep_attribution")
    class KeepIptc : Flag("keep_iptc")
    class LayerApply : Flag("layer_apply")
    class Lossy : Flag("lossy")
    class NoOverflow : Flag("no_overflow")
    class PreserveTransparency : Flag("preserve_transparency")
    class Png8 : Flag("png8")
    class Png24 : Flag("png24")
    class Png32 : Flag("png32")
    class Progressive(mode: ProgressiveMode? = null) : Flag("progressive", mode)
    class Rasterize : Flag("rasterize")
    class RegionRelative : Flag("region_relative")
    class Relative : Flag("relative")
    class ReplaceImage : Flag("replace_image")
    class Sanitize : Flag("sanitize")
    class StripProfile : Flag("strip_profile")
    class TextNoTrim : Flag("text_no_trim")
    class TextDisallowOverflow : Flag("text_disallow_overflow")
    class Tiff8Lzw : Flag("tiff8_lzw")
    class Tiled : Flag("tiled")

    class Animated : Flag("animated")
    class StreamingAttachment : Flag("streaming_attachment")
    class HlsV3 : Flag("hlsv3")
    class KeepDar : Flag("keep_dar")
    class NoStream : Flag("no_stream")
    class Mono : Flag("mono")
    class Splice : Flag("splice")
    class TruncateTS : Flag("truncate_ts")
    class Waveform : Flag("waveform")
}

enum class ProgressiveMode(private val value: String) {
    Semi("semi"),
    Steep("steep"),
    None("none");

    override fun toString(): String {
        return value
    }
}