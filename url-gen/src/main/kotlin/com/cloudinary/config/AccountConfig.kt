package com.cloudinary.config

interface IAccountConfig {
    val cloudName: String
    val apiKey: String?
    val apiSecret: String?
}

data class AccountConfig(
    override val cloudName: String,
    override val apiKey: String? = null,
    override val apiSecret: String? = null
) : IAccountConfig