package com.cloudinary

import com.cloudinary.config.ApiConfig
import com.cloudinary.http.ApacheHttpClient45Factory
import com.cloudinary.http.HttpClientFactory
import com.cloudinary.http.HttpUrlConnectionFactory
import com.cloudinary.http.OkHttpClientFactory
import com.cloudinary.upload.Uploader

private const val CLIENT_FACTORY_EXTENSION_NAME = "cld.HttpClientFactory"

fun Cloudinary.uploader() = Uploader(this)

internal var Cloudinary.httpClientFactory: HttpClientFactory
    get() = getExtension(
        CLIENT_FACTORY_EXTENSION_NAME,
        instantiateClientFactory(userAgent, config.apiConfig)
    ) as HttpClientFactory
    set(value) = setExtension(CLIENT_FACTORY_EXTENSION_NAME, value)

private fun instantiateClientFactory(
    userAgent: String,
    apiConfig: ApiConfig
) = run {
    when {
        exists("org.apache.http.impl.client.HttpClientBuilder") -> ApacheHttpClient45Factory(
            userAgent,
            apiConfig
        )
        exists("okhttp3.OkHttpClient") -> OkHttpClientFactory(userAgent, apiConfig)
        else -> HttpUrlConnectionFactory(userAgent, apiConfig)
    }
}

private fun exists(factoryCanonicalName: String): Boolean {
    return try {
        Class.forName(factoryCanonicalName)
        true
    } catch (ignored: Exception) {
        false
    }
}