package com.cloudinary.upload.request.params

import com.cloudinary.transformation.Transformation
import com.cloudinary.upload.EagerTransformation
import com.cloudinary.upload.request.UploaderDsl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@UploaderDsl
data class UploadParams( //45
    @Json(name = "backup") internal val backup: Boolean? = null,
    @Json(name = "exif") internal val exif: Boolean? = null,
    @Json(name = "faces") internal val faces: Boolean? = null,
    @Json(name = "colors") internal val colors: Boolean? = null,
    @Json(name = "image_metadata") internal val imageMetadata: Boolean? = null,
    @Json(name = "use_filename") internal val useFilename: Boolean? = null,
    @Json(name = "unique_filename") internal val uniqueFilename: Boolean? = null,
    @Json(name = "eager_async") internal val eagerAsync: Boolean? = null,
    @Json(name = "invalidate") internal val invalidate: Boolean? = null,
    @Json(name = "discard_original_filename") internal val discardOriginalFilename: Boolean? = null,
    @Json(name = "overwrite") internal val overwrite: Boolean? = null,
    @Json(name = "phash") internal val phash: Boolean? = null,
    @Json(name = "return_delete_token") internal val returnDeleteToken: Boolean? = null,
    @Json(name = "async") internal val async: Boolean? = null,
    @Json(name = "type") internal val type: String? = null,
    @Json(name = "upload_preset") internal val uploadPreset: String? = null,
    @Json(name = "public_id") internal val publicId: String? = null,
    @Json(name = "callback") internal val callback: String? = null,
    @Json(name = "format") internal val format: String? = null,
    @Json(name = "notification_url") internal val notificationUrl: String? = null,
    @Json(name = "eager_notification_url") internal val eagerNotificationUrl: String? = null,
    @Json(name = "proxy") internal val proxy: String? = null,
    @Json(name = "folder") internal val folder: String? = null,
    @Json(name = "allowed_formats") internal val allowedFormats: List<String>? = null,
    @Json(name = "moderation") internal val moderation: String? = null,
    @Json(name = "cinemagraph_analysis") internal val cinemagraphAnalysis: Boolean? = null,
    @Json(name = "quality_analysis") internal val qualityAnalysis: Boolean? = null,


    @Json(name = "access_mode") internal val accessMode: String? = null,
    @Json(name = "responsive_breakpoints") internal val responsiveBreakpoints: List<ResponsiveBreakpoint>? = null,
    @Json(name = "transformation") internal val transformation: Transformation? = null,
    @Json(name = "eager") internal val eager: List<EagerTransformation>? = null,

    @Json(name = "signature") internal val signature: String? = null,
    @Json(name = "timestamp") internal val timestamp: Long? = null,

    // process write params:
    @Json(name = "headers") internal val headers: String? = null,
    @Json(name = "tags") internal val tags: List<String>? = null,
    @Json(name = "face_coordinates") internal val faceCoordinates: Coordinates? = null,
    @Json(name = "custom_coordinates") internal val customCoordinates: Coordinates? = null,
    @Json(name = "context") internal val context: Map<String, Any>? = null,
    @Json(name = "access_control") internal val accessControl: List<AccessControlRule>? = null,
    @Json(name = "ocr") internal val ocr: String? = null,
    @Json(name = "raw_convert") internal val rawConvert: String? = null,
    @Json(name = "categorization") internal val categorization: String? = null,
    @Json(name = "detection") internal val detection: String? = null,
    @Json(name = "similarity_search") internal val similaritySearch: String? = null,
    @Json(name = "background_removal") internal val backgroundRemoval: String? = null,
    @Json(name = "auto_tagging") internal val autoTagging: String? = null,
    @Json(name = "accessibility_analysis") internal var accessibilityAnalysis: Boolean? = null,
    @Json(name = "filename_override") internal val filenameOverride: String? = null
) {

    @Suppress("unused", "MemberVisibilityCanBePrivate")
    @UploaderDsl
    class Builder {
        var backup: Boolean? = null
        var exif: Boolean? = null
        var faces: Boolean? = null
        var colors: Boolean? = null
        var imageMetadata: Boolean? = null
        var useFilename: Boolean? = null
        var uniqueFilename: Boolean? = null
        var eagerAsync: Boolean? = null
        var invalidate: Boolean? = null
        var discardOriginalFilename: Boolean? = null
        var overwrite: Boolean? = null
        var phash: Boolean? = null
        var returnDeleteToken: Boolean? = null
        var async: Boolean? = null
        var type: String? = null
        var uploadPreset: String? = null
        var publicId: String? = null
        var callback: String? = null
        var format: String? = null
        var notificationUrl: String? = null
        var eagerNotificationUrl: String? = null
        var proxy: String? = null
        var folder: String? = null
        var allowedFormats: List<String>? = null
        var moderation: String? = null
        var accessMode: String? = null
        var responsiveBreakpoints: List<ResponsiveBreakpoint>? = null
        var transformation: Transformation? = null
        var eager: List<EagerTransformation>? = null
        var headers: String? = null
        var signature: String? = null
        var timestamp: Long? = null
        var tags: List<String>? = null
        var faceCoordinates: Coordinates? = null
        var customCoordinates: Coordinates? = null
        var context: Map<String, Any>? = null
        var accessControl: List<AccessControlRule>? = null
        var ocr: String? = null
        var rawConvert: String? = null
        var categorization: String? = null
        var detection: String? = null
        var similaritySearch: String? = null
        var backgroundRemoval: String? = null
        var autoTagging: String? = null
        var cinemagraphAnalysis: Boolean? = null
        var qualityAnalysis: Boolean? = null
        var accessibilityAnalysis: Boolean? = null
        var filenameOverride: String? = null

        fun transformation(transform: Transformation.Builder.() -> Unit) {
            val builder = Transformation.Builder()
            builder.transform()
            transformation = builder.build()
        }

        fun build(): UploadParams {
            return UploadParams(
                backup,
                exif,
                faces,
                colors,
                imageMetadata,
                useFilename,
                uniqueFilename,
                eagerAsync,
                invalidate,
                discardOriginalFilename,
                overwrite,
                phash,
                returnDeleteToken,
                async,
                type,
                uploadPreset,
                publicId,
                callback,
                format,
                notificationUrl,
                eagerNotificationUrl,
                proxy,
                folder,
                allowedFormats,
                moderation,
                cinemagraphAnalysis,
                qualityAnalysis,
                accessMode,
                responsiveBreakpoints,
                transformation,
                eager,
                signature,
                timestamp,
                headers,
                tags,
                faceCoordinates,
                customCoordinates,
                context,
                accessControl,
                ocr,
                rawConvert,
                categorization,
                detection,
                similaritySearch,
                backgroundRemoval,
                autoTagging,
                accessibilityAnalysis,
                filenameOverride
            )
        }
    }
}