package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.ContextResult
import com.cloudinary.util.buildContextParams
import com.cloudinary.util.toContextResult

class ContextRequest internal constructor(
    uploader: Uploader,
    options: UploaderOptions,
    cloudinaryConfig: CloudinaryConfig,
    private val command: ContextCommand,
    private val type: String?,
    private val context: Map<String, Any>,
    private val publicIds: List<String>
) : AbstractUploaderRequest<ContextResult>(uploader, options, cloudinaryConfig) {
    override fun buildParams() = buildContextParams(command, type, if (context.isEmpty()) null else context, publicIds)
    override fun execute() = uploader.callApi(this, "context", ::toContextResult)
    class Builder(private val command: ContextCommand, private val publicIds: List<String>, uploader: Uploader) :
        UploaderRequestsBuilder<ContextRequest>(uploader) {
        private val context = mutableMapOf<String, Any>()
        var type: String? = null
        fun context(vararg pairs: Pair<String, Any>) = apply { context.putAll(pairs) }
        override fun build() = ContextRequest(uploader, options, cloudinaryConfig, command, type, context, publicIds)
    }
}

enum class ContextCommand(private val value: String) {
    Add("add"),
    Remove("remove"),
    Replace("replace"),
    RemoveAll("remove_all");

    override fun toString() = value
}
