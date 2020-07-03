package com.cloudinary.config

const val DEFAULT_CHUNK_SIZE = 20_000_000 // bytes
const val DEFAULT_READ_TIMEOUT = 0
const val DEFAULT_CONNECTION_TIMEOUT = 60 // seconds
const val DEFAULT_UPLOAD_PREFIX = "https://api.cloudinary.com"

const val UPLOAD_PREFIX = "upload_prefix"
const val CHUNK_SIZE = "chunk_size"
const val READ_TIMEOUT = "read_timeout"
const val CONNECT_TIMEOUT = "connect_timeout"
const val CALLBACK_URL = "callback_url"

interface IApiConfig {
    val uploadPrefix: String
    val chunkSize: Int
    val readTimeout: Int
    val connectionTimeout: Int
    val callbackUrl: String?
}

data class ApiConfig(
    override val uploadPrefix: String = DEFAULT_UPLOAD_PREFIX,
    override val chunkSize: Int = DEFAULT_CHUNK_SIZE,
    override val readTimeout: Int = DEFAULT_READ_TIMEOUT,
    override val connectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT,
    override val callbackUrl: String? = null
) : IApiConfig {
    constructor(params: Map<String, Any>) : this(
        uploadPrefix = params[UPLOAD_PREFIX]?.toString() ?: DEFAULT_UPLOAD_PREFIX,
        chunkSize = params.getInt(CHUNK_SIZE) ?: DEFAULT_CHUNK_SIZE,
        readTimeout = params.getInt(READ_TIMEOUT) ?: DEFAULT_READ_TIMEOUT,
        connectionTimeout = params.getInt(CONNECT_TIMEOUT) ?: DEFAULT_CONNECTION_TIMEOUT,
        callbackUrl = params[CALLBACK_URL]?.toString()
    )
}
