package com.cloudinary.config

import com.cloudinary.AuthToken
import java.net.URI

data class Configuration(
    val accountConfig: CloudConfig,
    val urlConfig: UrlConfig,
    val apiConfig: ApiConfig
) : IUrlConfig by urlConfig, ICloudConfig by accountConfig, IApiConfig by apiConfig {

    companion object {
        fun fromUri(uri: String): Configuration {

            val params = parseConfigUrl(uri)

            val accountConfig = CloudConfig(
                cloudName = params["cloud_name"] as String,
                apiKey = params["api_key"]?.toString(),
                apiSecret = params["api_secret"]?.toString()
            )

            val urlConfig = UrlConfig(
                secureDistribution = params["secure_distribution"]?.toString(),
                privateCdn = params.getBoolean("private_cdn") ?: DEFAULT_PRIVATE_CDN,
                cdnSubdomain = params.getBoolean("cdn_subdomain") ?: DEFAULT_CDN_SUBDOMAIN,
                shorten = params.getBoolean("shorten") ?: DEFAULT_SHORTEN,
                secureCdnSubdomain = params.getBoolean("secure_cdn_subdomain") ?: DEFAULT_SECURE_CDN_SUBDOMAIN,
                useRootPath = params.getBoolean("use_root_path") ?: DEFAULT_USE_ROOT_PATH,
                cname = params["cname"]?.toString(),
                secure = params.getBoolean("secure") ?: DEFAULT_SECURE,
                authToken = params["auth_token"]?.let { if (it is Map<*, *>) AuthToken.fromParams(it) else null }
            )
            val apiConfig = ApiConfig(
                uploadPrefix = params["upload_prefix"]?.toString(),
                chunkSize = params.getInt("chunk_size") ?: DEFAULT_CHUNK_SIZE,
                readTimeout = params.getInt("read_timeout") ?: DEFAULT_READ_TIMEOUT,
                connectTimeout = params.getInt("connect_timeout") ?: DEFAULT_CONNECT_TIMEOUT
            )

            return Configuration(
                accountConfig,
                urlConfig,
                apiConfig
            )
        }
    }
}

private fun Map<String, Any>.getBoolean(key: String) =
    when (val obj = this[key]) {
        is Boolean -> obj
        is String -> obj.toBoolean()
        else -> null
    }

private fun Map<String, Any>.getInt(key: String) =
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
