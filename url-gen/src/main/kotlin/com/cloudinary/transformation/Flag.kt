package com.cloudinary.transformation

open class FlagsParam(flag: FlagKey) : Param("flags", "fl", ParamValue(flag)) {
    // flag param is allowed to appear several time per action, so the hashing key includes the flag value itself.
    private val _hashKey: String = "fl_$flag"

    override val hashKey = _hashKey
//    internal fun merge(flag: FlagKey?) = if (flag == null) this else FlagsParam(this.value.values + flag.value)
}


sealed class FlagKey(vararg items: Any?) : ParamValue(items.toList().filterNotNull()) {
    class ANY_FORMAT : FlagKey("any_format")
    class ATTACHMENT : FlagKey("attachment")
    class APNG : FlagKey("apng")
    class AWEBP : FlagKey("awebp")
    class CLIP : FlagKey("clip")
    class CLIP_EVENODD : FlagKey("clip_evenodd")
    class CUTTER : FlagKey("cutter")
    class FORCE_STRIP : FlagKey("force_strip")
    class FORCE_ICC : FlagKey("force_icc")
    class GETINFO : FlagKey("getinfo")
    class IGNORE_ASPECT_RATIO : FlagKey("ignore_aspect_ratio")
    class IMMUTABLE_CACHE : FlagKey("immutable_cache")
    class KEEP_ATTRIBUTION : FlagKey("keep_attribution")
    class KEEP_IPTC : FlagKey("keep_iptc")
    class LAYER_APPLY : FlagKey("layer_apply")
    class LOSSY : FlagKey("lossy")
    class NO_OVERFLOW : FlagKey("no_overflow")
    class PRESERVE_TRANSPARENCY : FlagKey("preserve_transparency")
    class PNG8 : FlagKey("png8")
    class PNG24 : FlagKey("png24")
    class PNG32 : FlagKey("png32")
    class PROGRESSIVE(mode: ProgressiveMode? = null) : FlagKey("progressive", mode)
    class RASTERIZE : FlagKey("rasterize")
    class REGION_RELATIVE : FlagKey("region_relative")
    class RELATIVE : FlagKey("relative")
    class REPLACE_IMAGE : FlagKey("replace_image")
    class SANITIZE : FlagKey("sanitize")
    class STRIP_PROFILE : FlagKey("strip_profile")
    class TEXT_NO_TRIM : FlagKey("text_no_trim")
    class TEXT_DISALLOW_OVERFLOW : FlagKey("text_disallow_overflow")
    class TIFF8_LZW : FlagKey("tiff8_lzw")
    class TILED : FlagKey("tiled")

    class ANIMATED : FlagKey("animated")
    class STREAMING_ATTACHMENT : FlagKey("streaming_attachment")
    class HLSV3 : FlagKey("hlsv3")
    class KEEP_DAR : FlagKey("keep_dar")
    class NO_STREAM : FlagKey("no_stream")
    class MONO : FlagKey("mono")
    class SPLICE : FlagKey("splice")
    class TRUNCATE_TS : FlagKey("truncate_ts")
    class WAVEFORM : FlagKey("waveform")
}

enum class ProgressiveMode(private val value: String) {
    SEMI("semi"),
    STEEP("steep"),
    NONE("none");

    override fun toString(): String {
        return value
    }
}