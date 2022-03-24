package com.cloudinary.upload

import com.cloudinary.upload.request.*
import com.cloudinary.upload.response.*
import java.io.File
import java.io.InputStream
import java.net.URL

// TODO pending requirements
// fun Uploader.text()

fun Uploader.upload(file: File, request: (UploadRequest.Builder.() -> Unit)? = null) =
    buildAndExecute(UploadRequest.Builder(file, this), request)

fun Uploader.upload(file: URL, request: (UploadRequest.Builder.() -> Unit)? = null) =
    buildAndExecute(UploadRequest.Builder(file, this), request)

fun Uploader.upload(file: String, request: (UploadRequest.Builder.() -> Unit)? = null) =
    buildAndExecute(UploadRequest.Builder(file, this), request)

fun Uploader.upload(file: InputStream, request: (UploadRequest.Builder.() -> Unit)? = null) =
    buildAndExecute(UploadRequest.Builder(file, this), request)

fun Uploader.upload(file: ByteArray, request: (UploadRequest.Builder.() -> Unit)? = null) =
    buildAndExecute(UploadRequest.Builder(file, this), request)

fun Uploader.destroy(
    publicId: String,
    request: (DestroyRequest.Builder.() -> Unit)? = null
): UploaderResponse<StatusResult> {
    val builder = DestroyRequest.Builder(publicId, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.rename(
    fromPublicId: String,
    toPublicId: String,
    request: (RenameRequest.Builder.() -> Unit)? = null
): UploaderResponse<UploadResult> {
    val builder = RenameRequest.Builder(fromPublicId, toPublicId, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.explicit(
    publicId: String,
    request: (ExplicitRequest.Builder.() -> Unit)? = null
): UploaderResponse<UploadResult> {
    val builder = ExplicitRequest.Builder(publicId, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.deleteByToken(
    token: String,
    request: (DeleteByTokenRequest.Builder.() -> Unit)? = null
): UploaderResponse<StatusResult> {
    val builder = DeleteByTokenRequest.Builder(token, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.generateSprite(
    tag: String,
    request: (GenerateSpriteRequest.Builder.() -> Unit)? = null
): UploaderResponse<GenerateSpriteResult> {
    val builder = GenerateSpriteRequest.Builder(tag, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.multi(tag: String, request: (MultiRequest.Builder.() -> Unit)? = null): UploaderResponse<MultiResult> {
    val builder = MultiRequest.Builder(tag, this)
    request?.let { builder.request() }
    return builder.build().execute()
}

fun Uploader.createArchive(request: (ArchiveRequest.Builder.() -> Unit)): UploaderResponse<ArchiveResult> {
    val builder = ArchiveRequest.Builder(this)
    builder.request()
    return builder.build().execute()
}

fun Uploader.addTag(
    tag: String,
    publicIds: List<String>,
    exclusive: Boolean = false,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val command = if (exclusive) TagsCommand.SetExclusive else TagsCommand.Add
    val builder = TagsRequest.Builder(command, publicIds, this)
    builder.tag = listOf(tag)
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.addTag(
    tag: List<String>,
    publicIds: List<String>,
    exclusive: Boolean = false,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val command = if (exclusive) TagsCommand.SetExclusive else TagsCommand.Add
    val builder = TagsRequest.Builder(command, publicIds, this)
    builder.tag = tag
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.removeTag(
    tag: String,
    publicIds: List<String>,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val builder = TagsRequest.Builder(TagsCommand.Remove, publicIds, this)
    builder.tag = listOf(tag)
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.removeTag(
    tag: List<String>,
    publicIds: List<String>,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val builder = TagsRequest.Builder(TagsCommand.Remove, publicIds, this)
    builder.tag = tag
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.removeAllTags(
    publicIds: List<String>,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val builder = TagsRequest.Builder(TagsCommand.RemoveAll, publicIds, this)
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.replaceTag(
    tag: String,
    publicIds: List<String>,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val builder = TagsRequest.Builder(TagsCommand.Replace, publicIds, this)
    builder.tag = listOf(tag)
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.replaceTag(
    tag: List<String>,
    publicIds: List<String>,
    request: (TagsRequest.Builder.() -> Unit)? = null
): UploaderResponse<TagsResult> {
    val builder = TagsRequest.Builder(TagsCommand.Replace, publicIds, this)
    builder.tag = tag
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.addContext(
    publicIds: List<String>,
    request: (ContextRequest.Builder.() -> Unit)? = null
): UploaderResponse<ContextResult> {
    val command = ContextCommand.Add
    val builder = ContextRequest.Builder(command, publicIds, this)
    request?.let { builder.it() }
    return builder.build().execute()
}

fun Uploader.removeAllContext(
    publicIds: List<String>,
    request: (ContextRequest.Builder.() -> Unit)? = null
): UploaderResponse<ContextResult> {
    val builder = ContextRequest.Builder(ContextCommand.RemoveAll, publicIds, this)
    request?.let { builder.it() }
    return builder.build().execute()
}