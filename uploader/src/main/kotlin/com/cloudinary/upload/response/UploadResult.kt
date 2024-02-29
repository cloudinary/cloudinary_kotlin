package com.cloudinary.upload.response

import com.cloudinary.upload.request.params.AccessControlRule
import com.cloudinary.upload.request.params.Coordinates
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UploadResult(
    @Json(name = "public_id")
    var publicId: String? = null,
    @Json(name = "version")
    var version: Int? = null,
    @Json(name = "signature")
    var signature: String? = null,
    @Json(name = "width")
    var width: Int? = null,
    @Json(name = "height")
    var height: Int? = null,
    @Json(name = "format")
    var format: String? = null,
    @Json(name = "resource_type")
    var resourceType: String? = null,
    @Json(name = "created_at")
    var createdAt: String? = null,
    @Json(name = "tags")
    var tags: List<String>? = null,
    @Json(name = "pages")
    var pages: Int? = null,
    @Json(name = "bytes")
    var bytes: Int? = null,
    @Json(name = "type")
    var type: String? = null,
    @Json(name = "etag")
    var etag: String? = null,
    @Json(name = "placeholder")
    var placeholder: Boolean? = null,
    @Json(name = "url")
    var url: String? = null,
    @Json(name = "secure_url")
    var secureUrl: String? = null,
    @Json(name = "access_mode")
    var accessMode: String? = null,
    @Json(name = "original_filename")
    var originalFilename: String? = null,
    @Json(name = "colors")
    var colors: List<ResultColor>? = null,
    @Json(name = "predominant")
    var predominant: Map<String, List<ResultColor>>? = null,
    @Json(name = "eager")
    var eager: List<ResultEager>? = null,
    var status: String? = null,
    var faces: Coordinates? = null,
    var coordinates: Map<String, Coordinates>? = null,
    var moderation: List<ResultModeration>? = null,
    @Json(name = "responsive_breakpoints")
    var responsiveBreakpoints: List<ResultResponsiveBreakpoints>? = null,
    @Json(name = "access_control")
    var accessControl: List<AccessControlRule>? = null,
    @Json(name = "quality_analysis")
    var qualityAnalysis: ResultQualityAnalysis? = null,
    @Json(name = "cinemagraph_analysis")
    var cinemagraphAnalysis: ResultCinemagraphAnalysis? = null,
    @Json(name = "delete_token")
    var deleteToken: String? = null,
    var context: ResultContext? = null,
    var done: Boolean? = null,
    @Json(name = "accessibility_analysis")
    var accessibilityAnalysis: ResultAccessibilityAnalysis? = null,
    @Json(name = "image_metadata")
    var imageMetadata: Map<String, String>? = null,
    @Json(name = "metadata")
    var metadata: Map<String, Any>? = null,
    @Json(name = "video")
    var video: VideoResultObject? = null,
    @Json(name = "audio")
    var audio: AudioResultObject? = null
)

class ResultColor(val color: String, val percent: Float)

@JsonClass(generateAdapter = true)
class ResultEager(
    val transformation: String?,
    val width: Int?,
    val height: Int?,
    val bytes: Long?,
    val format: String?,
    val url: String?,
    @Json(name = "secure_url")
    val secureUrl: String?
)

@JsonClass(generateAdapter = true)
data class ResultModeration(val kind: String, val status: String)

@JsonClass(generateAdapter = true)
data class ResultResponsiveBreakpoints(
    val breakpoints: List<ResultSingleBreakpointData>,
    val transformation: String?
)

@JsonClass(generateAdapter = true)
data class ResultSingleBreakpointData(
    val bytes: Int, val width: Int, val height: Int, val url: String, @Json(name = "secure_url")
    val secureUrl: String
)

@JsonClass(generateAdapter = true)
data class ResultQualityAnalysis(val focus: Float)

@JsonClass(generateAdapter = true)
data class ResultCinemagraphAnalysis(@Json(name = "cinemagraph_score") val score: Float)

@JsonClass(generateAdapter = true)
data class ResultContext(
    val custom: Map<String, String>?
)

@JsonClass(generateAdapter = true)
data class ResultAccessibilityAnalysis(
    @Json(name = "colorblind_accessibility_analysis")
    val colorblindAccessibilityAnalysis: ResultColorblindAccessibilityScore,
    @Json(name = "colorblind_accessibility_score")
    val colorblindAccessibilityScore: Float
)

@JsonClass(generateAdapter = true)
data class VideoResultObject(
    @Json(name = "pix_format")
    val pixFormat: String?,
    @Json(name = "codec")
    val codec: String?,
    @Json(name = "level")
    val level: Float?,
    @Json(name = "profile")
    val profile: String?,
    @Json(name = "bit_rate")
    val bitRate: String?,
    @Json(name = "time_base")
    val timeBase: String?,
    @Json(name = "metadata")
    var metadata: Map<String, Any>?
)

@JsonClass(generateAdapter = true)
data class AudioResultObject(
    @Json(name = "codec")
    val codec: String?,
    @Json(name = "bit_rate")
    val bitRate: String?,
    @Json(name = "frequency")
    val frequency: Int?,
    @Json(name = "channels")
    val channels: Int?,
    @Json(name = "channel_layout")
    val channelLayout: String?
)


@JsonClass(generateAdapter = true)
data class ResultColorblindAccessibilityScore(
    @Json(name = "distinct_edges")
    val distinctEdges: Float,
    @Json(name = "distinct_colors")
    val distinctColors: Float,
    @Json(name = "most_indistinct_pair")
    val mostIndistinctPair: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultColorblindAccessibilityScore

        if (distinctEdges != other.distinctEdges) return false
        if (distinctColors != other.distinctColors) return false
        if (!mostIndistinctPair.contentEquals(other.mostIndistinctPair)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = distinctEdges.hashCode()
        result = 31 * result + distinctColors.hashCode()
        result = 31 * result + mostIndistinctPair.contentHashCode()
        return result
    }
}
