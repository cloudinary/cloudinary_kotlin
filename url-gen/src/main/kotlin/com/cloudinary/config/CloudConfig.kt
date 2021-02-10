package com.cloudinary.config

const val CLOUD_NAME = "cloud_name"
const val API_KEY = "api_key"
const val API_SECRET = "api_secret"

interface ICloudConfig {
    val cloudName: String?
    val apiKey: String?
    val apiSecret: String?
}

data class CloudConfig(
    override val cloudName: String? = null,
    override val apiKey: String? = null,
    override val apiSecret: String? = null
) : ICloudConfig {
    constructor(params: Map<String, Any>) : this(
        cloudName = params[CLOUD_NAME] as String,
        apiKey = params[API_KEY]?.toString(),
        apiSecret = params[API_SECRET]?.toString()
    )
}