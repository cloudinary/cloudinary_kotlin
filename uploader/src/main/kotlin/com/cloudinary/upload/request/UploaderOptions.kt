package com.cloudinary.upload.request


class UploaderOptions internal constructor(
    val headers: Map<String, String>,
    val resourceType: String?,
    val filename: String?,
    val chunkSize: Int?,
    val unsigned: Boolean
) {

    @UploaderDsl
    class Builder {
        var headers = emptyMap<String, String>()
        var resourceType: String? = null
        var filename: String? = null
        var chunkSize: Int? = null
        var unsigned: Boolean = false

        fun build() = UploaderOptions(
            headers,
            resourceType,
            filename,
            chunkSize,
            unsigned
        )
    }
}