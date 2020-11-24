package com.cloudinary.config

import java.net.URI

data class Configuration(
    val cloudConfig: CloudConfig,
    val urlConfig: UrlConfig,
    val apiConfig: ApiConfig,
    val authTokenConfig: AuthTokenConfig?
) : IUrlConfig by urlConfig, ICloudConfig by cloudConfig, IApiConfig by apiConfig {

    companion object {
        fun fromUri(uri: String): Configuration {

            val params = parseConfigUrl(uri)

            val cloudConfig = CloudConfig(params)
            val urlConfig = UrlConfig(params)
            val apiConfig = ApiConfig(params)

            val authTokenConfig = (params["auth_token"] as? Map<*, *>)?.let {
                AuthTokenConfig(it)
            }

            return Configuration(
                cloudConfig,
                urlConfig,
                apiConfig,
                authTokenConfig
            )
        }
    }
}

internal fun Map<String, Any>.getBoolean(key: String) =
    when (val obj = this[key]) {
        is Boolean -> obj
        is String -> obj.toBoolean()
        else -> null
    }

internal fun Map<String, Any>.getInt(key: String) =
    when (val obj = this[key]) {
        is Int -> obj
        is String -> obj.toInt()
        else -> null
    }

private fun parseConfigUrl(cloudinaryUrl: String): Map<String, Any> {
    require(cloudinaryUrl.isNotBlank()) { "Cloudinary url must not be blank" }
    require(cloudinaryUrl.startsWith("cloudinary://")) { "Cloudinary url must start with 'cloudinary://'" }

    val params = mutableMapOf<String, Any>()
    val uri = URI.create(cloudinaryUrl)
    params["cloud_name"] = uri.host

    uri.userInfo?.split(":")?.run {
        params["api_key"] = get(0)
        if (size > 1) params["api_secret"] = get(1)
    }

    params["private_cdn"] = !uri.path.isNullOrBlank()
    params["secure_distribution"] = uri.path
    uri.query?.let { updateMapFromQuery(params, it) }
    return params
}

private fun updateMapFromQuery(params: MutableMap<String, Any>, query: String) {
    query.split("&").forEach {
        val (key, value) = it.split("=")

        if (isNestedKey(key)) {
            putNestedValue(params, key, value)
        } else {
            params[key] = value
        }
    }
}

private fun putNestedValue(params: MutableMap<String, Any>, key: String, value: String) {
    val chain = key.split("[", "]").filter { it.isNotEmpty() }
    var outer = params
    var innerKey = chain[0]
    var i = 0
    while (i < chain.size - 1) {
        @Suppress("UNCHECKED_CAST") var inner: MutableMap<String, Any>? = outer[innerKey] as? MutableMap<String, Any>
        if (inner == null) {
            inner = mutableMapOf()
            outer[innerKey] = inner
        }
        outer = inner
        i++
        innerKey = chain[i]
    }

    outer[innerKey] = value
}

private fun isNestedKey(key: String): Boolean {
    return key.matches("\\w+\\[\\w+]".toRegex())
}
