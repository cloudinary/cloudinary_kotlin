package com.cloudinary.upload.request


private const val DEFAULT_CHUNK_SIZE = 20000000

class UploaderOptions internal constructor(
    val headers: Map<String, String>,
    val resourceType: String?,
    val filename: String?,
    val chunkSize: Int,
    val unsigned: Boolean
) {

    @UploaderDsl
    class Builder {
        var headers = emptyMap<String, String>()
        var resourceType: String? = null
        var filename: String? = null
        var chunkSize: Int = DEFAULT_CHUNK_SIZE
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