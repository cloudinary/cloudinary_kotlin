package com.cloudinary.config

import com.cloudinary.AuthToken

const val CLOUD_NAME = "cloud_name"
const val API_KEY = "api_key"
const val API_SECRET = "api_secret"
const val AUTH_TOKEN = "auth_token"

interface ICloudConfig {
    val cloudName: String
    val apiKey: String?
    val apiSecret: String?
    val authToken: AuthToken?
}

data class CloudConfig(
    override val cloudName: String,
    override val apiKey: String? = null,
    override val apiSecret: String? = null,
    override val authToken: AuthToken? = null
) : ICloudConfig {
    constructor(params: Map<String, Any>) : this(
        cloudName = params[CLOUD_NAME] as String,
        apiKey = params[API_KEY]?.toString(),
        apiSecret = params[API_SECRET]?.toString(),
        authToken = (params[AUTH_TOKEN] as? Map<*, *>)?.let { AuthToken(it) }
    )
}