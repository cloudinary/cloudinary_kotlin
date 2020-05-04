package com.cloudinary.transformation

open class FlagsParam(flag: FlagKey) : Param("flags", "fl", ParamValue(flag)) {
    // flag param is allowed to appear several time per action, so the hashing key includes the flag value itself.
    private val _hashKey: String = "fl_$flag"

    override val hashKey = _hashKey
}

// Make these objects instead of classes
sealed class FlagKey(vararg items: Any?) : ParamValue(items.toList().filterNotNull()) {
    class AnyFormat : FlagKey("any_format")
    class Attachment : FlagKey("attachment")
    class APng : FlagKey("apng")
    class AWebp : FlagKey("awebp")
    class Clip : FlagKey("clip")
    class ClipEvenOdd : FlagKey("clip_evenodd")
    class Cutter : FlagKey("cutter")
    class ForceStrip : FlagKey("force_strip")
    class ForceIcc : FlagKey("force_icc")
    class GetInfo : FlagKey("getinfo")
    class IgnoreAspectRatio : FlagKey("ignore_aspect_ratio")
    class ImmutableCache : FlagKey("immutable_cache")
    class KeepAttribution : FlagKey("keep_attribution")
    class KeepIptc : FlagKey("keep_iptc")
    class LayerApply : FlagKey("layer_apply")
    class Lossy : FlagKey("lossy")
    class NoOverflow : FlagKey("no_overflow")
    class PreserveTransparency : FlagKey("preserve_transparency")
    class Png8 : FlagKey("png8")
    class Png24 : FlagKey("png24")
    class Png32 : FlagKey("png32")
    class Progressive(mode: ProgressiveMode? = null) : FlagKey("progressive", mode)
    class Rasterize : FlagKey("rasterize")
    class RegionRelative : FlagKey("region_relative")
    class Relative : FlagKey("relative")
    class ReplaceImage : FlagKey("replace_image")
    class Sanitize : FlagKey("sanitize")
    class StripProfile : FlagKey("strip_profile")
    class TextNoTrim : FlagKey("text_no_trim")
    class TextDisallowOverflow : FlagKey("text_disallow_overflow")
    class Tiff8Lzw : FlagKey("tiff8_lzw")
    class Tiled : FlagKey("tiled")

    class Animated : FlagKey("animated")
    class StreamingAttachment : FlagKey("streaming_attachment")
    class HlsV3 : FlagKey("hlsv3")
    class KeepDar : FlagKey("keep_dar")
    class NoStream : FlagKey("no_stream")
    class Mono : FlagKey("mono")
    class Splice : FlagKey("splice")
    class TruncateTS : FlagKey("truncate_ts")
    class Waveform : FlagKey("waveform")

    fun asParam() = FlagsParam(this)
}

enum class ProgressiveMode(private val value: String) {
    Semi("semi"),
    Steep("steep"),
    None("none");

    override fun toString(): String {
        return value
    }
}