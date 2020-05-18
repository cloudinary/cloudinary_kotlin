package com.cloudinary.http

import com.cloudinary.config.ApiConfig

interface HttpClientFactory {
    val userAgent: String
    val config: ApiConfig

    fun getClient(): HttpClient
}