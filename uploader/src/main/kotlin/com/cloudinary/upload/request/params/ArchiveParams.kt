package com.cloudinary.upload.request.params

import com.cloudinary.upload.EagerTransformation
import com.cloudinary.upload.request.UploaderDsl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@UploaderDsl
class ArchiveParams(
    internal val type: String?,
    internal val mode: ArchiveMode,
    @Json(name = "target_format")
    internal val targetFormat: String?,
    @Json(name = "target_public_id")
    internal val targetPublicId: String?,
    @Json(name = "flatten_folders")
    internal val flattenFolders: Boolean,
    @Json(name = "flatten_transformations")
    internal val flattenTransformations: Boolean,
    @Json(name = "use_original_filename")
    internal val useOriginalFilename: Boolean,
    internal val async: Boolean,
    @Json(name = "keep_derived")
    internal val keepDerived: Boolean,
    @Json(name = "skip_transformation_name")
    internal val skipTransformationName: Boolean,
    @Json(name = "allow_missing")
    internal val allowMissing: Boolean,
    @Json(name = "notification_url")
    internal val notificationUrl: String?,
    @Json(name = "target_tags")
    internal val targetTags: List<String>?,
    internal val tags: List<String>?,
    @Json(name = "public_ids")
    internal val publicIds: List<String>?,
    @Json(name = "fully_qualified_public_ids")
    internal val fullyQualifiedPublicIds: List<String>?,
    internal val prefixes: List<String>?,
    internal val transformations: List<EagerTransformation>?,
    @Json(name = "expires_at")
    internal val expiresAt: Long?,
    internal val timestamp: Long
) {
    class Builder {
        var type: String? = null
        var mode: ArchiveMode = ArchiveMode.Create
        var targetFormat: String? = null
        var targetPublicId: String? = null
        var flattenFolders = false
        var flattenTransformations = false
        var useOriginalFilename = false
        var async = false
        var keepDerived = false
        var skipTransformationName = false
        var allowMissing = false
        var notificationUrl: String? = null
        var targetTags: List<String>? = null
        var tags: List<String>? = null
        var publicIds: List<String>? = null
        var fullyQualifiedPublicIds: List<String>? = null
        var prefixes: List<String>? = null
        var transformations: List<EagerTransformation>? = null
        var expiresAt: Long? = null
        var timestamp = System.currentTimeMillis() / 1000

        fun build() = ArchiveParams(
            type,
            mode,
            targetFormat,
            targetPublicId,
            flattenFolders,
            flattenTransformations,
            useOriginalFilename,
            async,
            keepDerived,
            skipTransformationName,
            allowMissing,
            notificationUrl,
            targetTags,
            tags,
            publicIds,
            fullyQualifiedPublicIds,
            prefixes,
            transformations,
            expiresAt,
            timestamp
        )
    }
}

@Suppress("unused")
enum class ArchiveMode(internal val value: String) {
    FormatZip("zip"),
    Download("download"),
    Create("create");

    override fun toString() = value
}