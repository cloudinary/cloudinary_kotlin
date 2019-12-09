package com.cloudinary.config

private const val DEFAULT_CHUNK_SIZE = 20_000_000L
private const val DEFAULT_READ_TIMEOUT = 0L
private const val DEFAULT_CONNECT_TIMEOUT = 0L

interface IApiConfig {
    val uploadPrefix: String?
    val chunkSize: Long
    val readTimeout: Long
    val connectTimeout: Long
}

data class ApiConfig(
    override val uploadPrefix: String? = null,
    override val chunkSize: Long = DEFAULT_CHUNK_SIZE,
    override val readTimeout: Long = DEFAULT_READ_TIMEOUT,
    override val connectTimeout: Long = DEFAULT_CONNECT_TIMEOUT
) : IApiConfig