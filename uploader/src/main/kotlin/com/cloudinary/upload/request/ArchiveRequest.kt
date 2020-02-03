package com.cloudinary.upload.request

import com.cloudinary.config.Configuration
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.params.ArchiveParams
import com.cloudinary.upload.response.ArchiveResult
import com.cloudinary.util.toArchiveResult
import com.cloudinary.util.toMap

class ArchiveRequest internal constructor(
    uploader: Uploader,
    options: UploaderOptions,
    configuration: Configuration,
    internal val params: ArchiveParams
) : AbstractUploaderRequest<ArchiveResult>(uploader, options, configuration) {
    override fun buildParams() = params.toMap()
    override fun execute() = uploader.callApi(this, "generate_archive", ::toArchiveResult)

    class Builder internal constructor(uploader: Uploader) :
        UploaderRequestsBuilder<ArchiveRequest>(uploader) {

        var params = ArchiveParams.Builder().build()

        fun params(params: ArchiveParams.Builder.() -> Unit) {
            val builder = ArchiveParams.Builder()
            builder.params()
            this.params = builder.build()
        }

        override fun build() = ArchiveRequest(uploader, options, configuration, params)
    }
}
