package com.cloudinary.config

interface ICloudConfig {
    val cloudName: String
    val apiKey: String?
    val apiSecret: String?
}

data class CloudConfig(
    override val cloudName: String,
    override val apiKey: String? = null,
    override val apiSecret: String? = null
) : ICloudConfig