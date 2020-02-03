package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.TagsResult
import com.cloudinary.util.buildTagsParams
import com.cloudinary.util.toTagsResult

class TagsRequest internal constructor(
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration,
    private val command: TagsCommand,
    private val tag: String?,
    private val type: String?,
    private val publicIds: List<String>
) : AbstractUploaderRequest<TagsResult>(uploader, options, configuration) {
    override fun buildParams() = buildTagsParams(command, tag, type, publicIds)
    override fun execute() = uploader.callApi(this, "tags", ::toTagsResult)
    class Builder(private val command: TagsCommand, private val publicIds: List<String>, uploader: Uploader) :
        UploaderRequestsBuilder<TagsRequest>(uploader) {
        var tag: String? = null
        var type: String? = null

        override fun build() = TagsRequest(uploader, options, configuration, command, tag, type, publicIds)
    }
}

enum class TagsCommand(private val value: String) {
    Add("add"),
    Remove("remove"),
    Replace("replace"),
    RemoveAll("remove_all"),
    SetExclusive("set_exclusive");

    override fun toString() = value
}
