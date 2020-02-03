package com.cloudinary.config

import com.cloudinary.AuthToken
import java.net.URI

private const val DEFAULT_CHUNK_SIZE = 20_000_000L
private const val DEFAULT_READ_TIMEOUT = 0L
private const val DEFAULT_CONNECT_TIMEOUT = 0L


data class Configuration(
    val accountConfig: AccountConfig,
    val urlConfig: UrlConfig,
    val apiConfig: ApiConfig
) : IUrlConfig by urlConfig, IAccountConfig by accountConfig, IApiConfig by apiConfig {

    companion object {
        fun fromUri(uri: String): Configuration {

            val params = parseConfigUrl(uri)

            val accountConfig = AccountConfig(
                cloudName = params["cloud_name"] as String,
                apiKey = params["api_key"] as? String,
                apiSecret = params["api_secret"] as? String
            )
            val urlConfig = UrlConfig(
                secureDistribution = params["secure_distribution"] as? String,
                privateCdn = params["private_cdn"] as? Boolean ?: DEFAULT_PRIVATE_CDN,
                cdnSubdomain = params["cdn_subdomain"] as? Boolean ?: DEFAULT_CDN_SUBDOMAIN,
                shorten = params["shorten"] as? Boolean ?: DEFAULT_SHORTEN,
                secureCdnSubdomain = params["secure_cdn_subdomain"] as? Boolean ?: DEFAULT_SECURE_CDN_SUBDOMAIN,
                useRootPath = params["use_root_path"] as? Boolean ?: DEFAULT_USE_ROOT_PATH,
                cname = params["cname"] as? String,
                secure = params["secure"] as? Boolean ?: DEFAULT_SECURE,
                authToken = params["auth_token"]?.let { if (it is Map<*, *>) AuthToken.fromParams(it) else null }
            )
            val uploadConfig = ApiConfig(
                uploadPrefix = params["upload_prefix"] as? String,
                chunkSize = params["chunk_size"] as? Long ?: DEFAULT_CHUNK_SIZE,
                readTimeout = params["read_timeout"] as? Long ?: DEFAULT_READ_TIMEOUT,
                connectTimeout = params["connect_timeout"] as? Long ?: DEFAULT_CONNECT_TIMEOUT
            )

            return Configuration(
                accountConfig,
                urlConfig,
                uploadConfig
            )
        }
    }
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
