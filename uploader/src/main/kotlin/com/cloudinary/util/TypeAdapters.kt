package com.cloudinary.util

import com.cloudinary.http.HttpResponse
import com.cloudinary.transformation.EagerTransformation
import com.cloudinary.transformation.Transformation
import com.cloudinary.upload.request.ContextCommand
import com.cloudinary.upload.request.TagsCommand
import com.cloudinary.upload.request.params.*
import com.cloudinary.upload.response.*
import com.squareup.moshi.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

internal fun UploadParams.toMap(): MutableMap<String, Any> {

    val params = HashMap<String, Any?>()
    params["public_id"] = publicId
    params["callback"] = callback
    params["format"] = format
    params["type"] = type
    params["backup"] = backup?.asCloudinaryBoolean()
    params["exif"] = exif?.asCloudinaryBoolean()
    params["faces"] = faces?.asCloudinaryBoolean()
    params["colors"] = colors?.asCloudinaryBoolean()
    params["image_metadata"] = imageMetadata?.asCloudinaryBoolean()
    params["use_filename"] = useFilename?.asCloudinaryBoolean()
    params["unique_filename"] = uniqueFilename?.asCloudinaryBoolean()
    params["eager_async"] = eagerAsync?.asCloudinaryBoolean()
    params["invalidate"] = invalidate?.asCloudinaryBoolean()
    params["overwrite"] = overwrite?.asCloudinaryBoolean()
    params["phash"] = phash?.asCloudinaryBoolean()
    params["return_delete_token"] = returnDeleteToken?.asCloudinaryBoolean()
    params["async"] = async?.asCloudinaryBoolean()
    params["notification_url"] = notificationUrl
    params["eager_notification_url"] = eagerNotificationUrl
    params["proxy"] = proxy
    params["folder"] = folder
    params["allowed_formats"] = allowedFormats?.joinToString(",")
    params["moderation"] = moderation
    params["access_mode"] = accessMode
    params["responsive_breakpoints"] = responsiveBreakpoints?.asResponsiveBreakpointParam()
    params["upload_preset"] = uploadPreset
    params["eager"] = eager?.asEagerParam()
    params["transformation"] = transformation?.toString()
    params["signature"] = signature
    params["timestamp"] = timestamp?.asCloudinaryTimestamp()
    params["quality_analysis"] = qualityAnalysis?.asCloudinaryBoolean()
    params["cinemagraph_analysis"] = cinemagraphAnalysis?.asCloudinaryBoolean()

    addWriteParams(params)

    val mutable = HashMap<String, Any>()
    params.entries.filter { it.value != null }.forEach { mutable[it.key] = it.value!! }

    return mutable
}

internal fun ArchiveParams.toMap(): MutableMap<String, Any> {
    val params = HashMap<String, Any?>()
    params["type"] = type
    params["mode"] = mode.toString()
    params["target_format"] = targetFormat
    params["target_public_id"] = targetPublicId
    params["flatten_folders"] = flattenFolders.asCloudinaryBoolean()
    params["flatten_transformations"] = flattenTransformations.asCloudinaryBoolean()
    params["use_original_filename"] = useOriginalFilename.asCloudinaryBoolean()
    params["async"] = async.asCloudinaryBoolean()
    params["keep_derived"] = keepDerived.asCloudinaryBoolean()
    params["skip_transformation_name"] = skipTransformationName.asCloudinaryBoolean()
    params["allow_missing"] = allowMissing.asCloudinaryBoolean()
    params["notification_url"] = notificationUrl
    params["target_tags"] = targetTags
    params["tags"] = tags
    params["public_ids"] = publicIds
    params["fully_qualified_public_ids"] = fullyQualifiedPublicIds
    params["prefixes"] = prefixes
    params["transformations"] = transformations?.asEagerParam()
    params["expires_at"] = expiresAt
    params["timestamp"] = timestamp

    val mutable = HashMap<String, Any>()
    params.entries.filter { it.value != null }.forEach { mutable[it.key] = it.value!! }

    return mutable
}

internal fun buildDeleteByTokenParams(token: String): MutableMap<String, Any> = mutableMapOf("token" to token)

@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildGenerateSpriteParams(
    tag: String,
    transformation: Transformation?,
    notificationUrl: String?,
    async: Boolean
) = mapOf(
    "tag" to tag,
    "transformation" to transformation?.toString(),
    "notification_url" to notificationUrl,
    "async" to async.asCloudinaryBoolean()
).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>

@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildTagsParams(
    command: TagsCommand,
    tag: String?,
    type: String?,
    publicIds: List<String>
) = mapOf(
    "command" to command.toString(),
    "tag" to tag,
    "type" to type,
    "public_ids" to publicIds
).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>

@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildContextParams(
    command: ContextCommand,
    type: String?,
    context: Map<String, Any>?,
    publicIds: List<String>
) = mapOf(
    "command" to command.toString(),
    "type" to type,
    "context" to context?.asContextParam(),
    "public_ids" to publicIds
).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>

@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildMultiParams(
    tag: String,
    transformation: Transformation?,
    format: String?,
    notificationUrl: String?,
    async: Boolean
) = mapOf(
    "tag" to tag,
    "transformation" to transformation?.toString(),
    "format" to format,
    "notification_url" to notificationUrl,
    "async" to async.asCloudinaryBoolean()
).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>


@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildRenameParams(
    fromPublicId: String,
    toPublicId: String,
    type: String?,
    toType: String?,
    overwrite: Boolean?,
    invalidate: Boolean?
) = mapOf(
    "from_public_id" to fromPublicId,
    "to_public_id" to toPublicId,
    "type" to type,
    "to_type" to toType,
    "overwrite" to overwrite?.asCloudinaryBoolean(),
    "invalidate" to invalidate?.asCloudinaryBoolean()
).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>

@Suppress("UNCHECKED_CAST") // this is a safe cast that the compiler can't recognize.
internal fun buildDestroyParams(publicId: String, type: String?, invalidate: Boolean?) =
    mapOf(
        "public_id" to publicId,
        "type" to type,
        "invalidate" to invalidate?.asCloudinaryBoolean()
    ).filterValues { it != null }.toMutableMap() as MutableMap<String, Any>

internal fun UploadParams.addWriteParams(params: MutableMap<String, Any?>) {
    params["headers"] = headers
    params["tags"] = tags?.joinToString(",")
    params["face_coordinates"] = faceCoordinates?.asCoordinatesParam()
    params["custom_coordinates"] = customCoordinates?.asCoordinatesParam()
    params["context"] = context?.asContextParam()
    params["access_control"] = accessControl?.asAccessControlParam()
    params["ocr"] = ocr
    params["raw_convert"] = rawConvert
    params["categorization"] = categorization
    params["detection"] = detection
    params["similarity_search"] = similaritySearch
    params["background_removal"] = backgroundRemoval
    params["auto_tagging"] = autoTagging // ObjectUtils.asFloat(options.get("auto_tagging")))
}

private fun Coordinates.asCoordinatesParam(): String {
    val rects = ArrayList<String>()
    for (rect in coordinates) {
        rects.add(rect.x.toString() + "," + rect.y + "," + rect.width + "," + rect.height)
    }

    return rects.joinToString("|")
}

private fun List<AccessControlRule>.asAccessControlParam(): String = this.toAccessControlJson()

internal fun Long.asCloudinaryTimestamp() = toString()

private fun Boolean.asCloudinaryBoolean() = toString()

private fun List<ResponsiveBreakpoint>.asResponsiveBreakpointParam() = toResponsiveBreakpointsJson()

private fun List<EagerTransformation>.asEagerParam() = joinToString("|", transform = { it.asEagerParam() })

private fun EagerTransformation.asEagerParam() = listOfNotNull(transformation.toString(), format).joinToString("/")

internal fun Map<String, Any?>.asContextParam(): String {
    return this.entries.joinToString("|") {
        val contextValue =
            when (val value = it.value) {
                is List<*> -> {
                    encodeList(value)
                }
                is Array<*> -> {
                    encodeList(this.toList())
                }
                else -> {
                    value.toString().cldEncodeSingleContextItem()
                }
            }

        "${it.key.cldEncodeSingleContextItem()}=$contextValue"
    }
}

private fun encodeList(list: List<*>): String {
    val builder = StringBuilder("[")
    var first = true
    for (s in list) {
        if (!first) {
            builder.append(",")
        }
        builder.append("\"").append(s.toString().cldEncodeSingleContextItem()).append("\"")
        first = false
    }
    return builder.append("]").toString()
}

private fun String.cldEncodeSingleContextItem() = replace("([=|])".toRegex(), "\\\\$1")

val moshi: Moshi = Moshi.Builder().add(DateAdapter()).build()

fun toContextResult(httpContent: String) = moshi.adapter<ContextResult>(ContextResult::class.java).fromJson(httpContent)
fun toTagsResult(httpContent: String) = moshi.adapter<TagsResult>(TagsResult::class.java).fromJson(httpContent)
fun toGenericResult(httpContent: String) = moshi.adapter<StatusResult>(StatusResult::class.java).fromJson(httpContent)
fun toArchiveResult(httpContent: String) = moshi.adapter<ArchiveResult>(ArchiveResult::class.java).fromJson(httpContent)

fun toGenerateSpriteResult(httpContent: String) =
    moshi.adapter<GenerateSpriteResult>(GenerateSpriteResult::class.java).fromJson(httpContent)

fun toMultiResult(httpContent: String) = moshi.adapter<MultiResult>(MultiResult::class.java).fromJson(httpContent)

fun toUploadResult(httpContent: String): UploadResult? {
    val moshi = Moshi.Builder()
        .add(ResponseColorsAdapter())
        .add(ResponseRectangleAdapter())
        .add(ResponseCoordinatesAdapter())
        .add(DateAdapter())
        .build()
    val jsonAdapter = moshi.adapter<UploadResult>(
        UploadResult::class.java
    )

    return jsonAdapter.fromJson(httpContent)
}

fun HttpResponse.uploadError(): UploadError? {
    return this.content?.let {
        val jsonAdapter = moshi.adapter<UploadError>(
            UploadError::class.java
        )
        jsonAdapter.fromJson(it)
    }
}

fun List<AccessControlRule>.toAccessControlJson(): String {
    val type: Type =
        Types.newParameterizedType(MutableList::class.java, AccessControlRule::class.java)
    val adapter: JsonAdapter<List<AccessControlRule>> = moshi.adapter(type)

    return adapter.toJson(this)
}

fun List<ResponsiveBreakpoint>.toResponsiveBreakpointsJson(): String {

    val customMoshi = moshi.newBuilder()
        .add(BreakpointsTransformationStringAdapter())
        .build()

    val type: Type =
        Types.newParameterizedType(MutableList::class.java, ResponsiveBreakpoint::class.java)
    val adapter: JsonAdapter<List<ResponsiveBreakpoint>> = customMoshi.adapter(type)

    return adapter.toJson(this)
}

class ResponseColorsAdapter {
    @FromJson
    fun fromJson(json: Array<String>) =
        ResultColor(json[0], json[1].toFloat())

    @ToJson
    fun toJson(color: ResultColor) = arrayOf(color.color, color.percent.toString())
}

class ResponseRectangleAdapter {
    @FromJson
    fun fromJson(json: Array<Int>) =
        Rectangle(json[0], json[1], json[2], json[3])

    @ToJson
    fun toJson(rectangle: Rectangle) = arrayOf(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
}

class ResponseCoordinatesAdapter {
    @FromJson
    fun fromJson(json: Array<Array<Int>>) =
        Coordinates(json.map {
            Rectangle(
                it[0],
                it[1],
                it[2],
                it[3]
            )
        }.toMutableList())

    @ToJson
    fun toJson(coordinates: Coordinates) =
        coordinates.coordinates.map { arrayOf(it.x, it.y, it.width, it.height) }.toTypedArray()
}

// Note: this is only relevant in responsive breakpoints transformations
class BreakpointsTransformationStringAdapter {
    @FromJson
    fun fromJson(json: String) = Transformation().add(json)

    @ToJson
    fun toJson(transformation: Transformation) = transformation.toString()
}

class DateAdapter {
    @FromJson
    fun fromJson(json: String) = json.toISO8601()

    @ToJson
    fun toJson(date: Date) = date.toISO8601()
}

private fun Date.toISO8601() = getDateFormat().format(this).toString()
private fun String.toISO8601() = getDateFormat().parseObject(this) as Date

private fun getDateFormat(): DateFormat {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat
}
