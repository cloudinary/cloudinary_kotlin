package com.cloudinary.config

internal const val DEFAULT_CHUNK_SIZE = 20_000_000
internal const val DEFAULT_READ_TIMEOUT = 0
internal const val DEFAULT_CONNECT_TIMEOUT = 0

interface IApiConfig {
    val uploadPrefix: String?
    val chunkSize: Int
    val readTimeout: Int
    val connectTimeout: Int
}

data class ApiConfig(
    override val uploadPrefix: String? = null,
    override val chunkSize: Int = DEFAULT_CHUNK_SIZE,
    override val readTimeout: Int = DEFAULT_READ_TIMEOUT,
    override val connectTimeout: Int = DEFAULT_CONNECT_TIMEOUT
) : IApiConfig