package com.cloudinary.upload.request

import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.http.ProgressCallback
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.response.UploaderResponse

abstract class AbstractUploaderRequest<T> internal constructor(
    protected val uploader: Uploader,
    val options: UploaderOptions,
    val cloudinaryConfig: CloudinaryConfig,
    val payload: Payload<*>? = null,
    internal val progressCallback: ProgressCallback? = null
) {
    abstract fun buildParams(): MutableMap<String, Any>
    abstract fun execute(): UploaderResponse<T>
}

@DslMarker
annotation class UploaderDsl

@UploaderDsl
abstract class UploaderRequestsBuilder<T>(protected val uploader: Uploader) {
    var cloudinaryConfig: CloudinaryConfig = uploader.cloudinary.config
    var options = UploaderOptions.Builder().build()

    fun options(options: UploaderOptions.Builder.() -> Unit) {
        val builder = UploaderOptions.Builder()
        builder.options()
        this.options = builder.build()
    }

    abstract fun build(): T
}