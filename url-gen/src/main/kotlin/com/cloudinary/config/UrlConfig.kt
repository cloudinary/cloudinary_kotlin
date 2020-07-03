package com.cloudinary.config

internal const val DEFAULT_PRIVATE_CDN = false
internal const val DEFAULT_CDN_SUBDOMAIN = false
internal const val DEFAULT_SHORTEN = false
internal const val DEFAULT_USE_ROOT_PATH = false
internal const val DEFAULT_SECURE = false // TODO make this TRUE and align tests.
internal const val DEFAULT_SECURE_CDN_SUBDOMAIN = false

const val SECURE_DISTRIBUTION = "secure_distribution"
const val PRIVATE_CDN = "private_cdn"
const val CDN_SUBDOMAIN = "cdn_subdomain"
const val SHORTEN = "shorten"
const val SECURE_CDN_SUBDOMAIN = "secure_cdn_subdomain"
const val USE_ROOT_PATH = "use_root_path"
const val CNAME = "cname"
const val SECURE = "secure"

interface IUrlConfig {
    val secureDistribution: String?
    val privateCdn: Boolean
    val cdnSubdomain: Boolean
    val shorten: Boolean
    val secureCdnSubdomain: Boolean
    val useRootPath: Boolean
    val cname: String?
    val secure: Boolean
}

data class UrlConfig(
    override val secureDistribution: String? = null,
    override val privateCdn: Boolean = DEFAULT_PRIVATE_CDN,
    override val cdnSubdomain: Boolean = DEFAULT_CDN_SUBDOMAIN,
    override val shorten: Boolean = DEFAULT_SHORTEN,
    override val secureCdnSubdomain: Boolean = DEFAULT_SECURE_CDN_SUBDOMAIN,
    override val useRootPath: Boolean = DEFAULT_USE_ROOT_PATH,
    override val cname: String? = null,
    override val secure: Boolean = DEFAULT_SECURE
) : IUrlConfig {
    constructor(params: Map<String, Any>) : this(
        secureDistribution = params[SECURE_DISTRIBUTION]?.toString(),
        privateCdn = params.getBoolean(PRIVATE_CDN) ?: DEFAULT_PRIVATE_CDN,
        cdnSubdomain = params.getBoolean(CDN_SUBDOMAIN) ?: DEFAULT_CDN_SUBDOMAIN,
        shorten = params.getBoolean(SHORTEN) ?: DEFAULT_SHORTEN,
        secureCdnSubdomain = params.getBoolean(SECURE_CDN_SUBDOMAIN) ?: DEFAULT_SECURE_CDN_SUBDOMAIN,
        useRootPath = params.getBoolean(USE_ROOT_PATH) ?: DEFAULT_USE_ROOT_PATH,
        cname = params[CNAME]?.toString(),
        secure = params.getBoolean(SECURE) ?: DEFAULT_SECURE

    )
}