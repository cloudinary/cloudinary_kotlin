package com.cloudinary.config

internal const val DEFAULT_PRIVATE_CDN = false
internal const val DEFAULT_CDN_SUBDOMAIN = false
internal const val DEFAULT_SHORTEN = false
internal const val DEFAULT_USE_ROOT_PATH = false
internal const val DEFAULT_SECURE_CDN_SUBDOMAIN = false

interface IUrlConfig {
    val secureDistribution: String?
    val privateCdn: Boolean
    val cdnSubdomain: Boolean
    val shorten: Boolean
    val secureCdnSubdomain: Boolean
    val useRootPath: Boolean
}

data class UrlConfig(
    override val secureDistribution: String? = null,
    override val privateCdn: Boolean = DEFAULT_PRIVATE_CDN,
    override val cdnSubdomain: Boolean = DEFAULT_CDN_SUBDOMAIN,
    override val shorten: Boolean = DEFAULT_SHORTEN,
    override val secureCdnSubdomain: Boolean = DEFAULT_SECURE_CDN_SUBDOMAIN,
    override val useRootPath: Boolean = DEFAULT_USE_ROOT_PATH
) : IUrlConfig