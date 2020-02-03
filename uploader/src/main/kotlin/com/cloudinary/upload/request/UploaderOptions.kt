package com.cloudinary.upload.request


private const val DEFAULT_CHUNK_SIZE = 20000000

class UploaderOptions internal constructor(
    internal val headers: Map<String, String>,
    internal val resourceType: String?,
    internal val filename: String?,
    internal val chunkSize: Int,
    internal val offset: Long,
    internal val unsigned: Boolean
) {

    @UploaderDsl
    class Builder {
        var headers = emptyMap<String, String>()
        var resourceType: String? = null
        var filename: String? = null
        var chunkSize: Int = DEFAULT_CHUNK_SIZE
        var offset: Long = 0
        var unsigned: Boolean = false

        fun build() = UploaderOptions(
            headers,
            resourceType,
            filename,
            chunkSize,
            offset,
            unsigned
        )
    }
}