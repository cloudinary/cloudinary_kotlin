package com.cloudinary.config

internal const val DEFAULT_PRIVATE_CDN = false
internal const val DEFAULT_CDN_SUBDOMAIN = false
internal const val DEFAULT_SHORTEN = false
internal const val DEFAULT_USE_ROOT_PATH = false
internal const val DEFAULT_SECURE = true
internal const val DEFAULT_FORCE_VERSION = true
internal const val DEFAULT_SECURE_CDN_SUBDOMAIN = false
internal const val DEFAULT_LONG_URL_SIGNATURE = false
internal const val DEFAULT_SIGN_URL = false
internal const val DEFAULT_SIGNATURE_ALGORITHM = "SHA-1"
internal const val DEFAULT_ANALYTICS = true

const val SIGN_URL = "sign_url"
const val LONG_URL_SIGNATURE = "long_url_signature"
const val FORCE_VERSION = "force_version"
const val SECURE_DISTRIBUTION = "secure_distribution"
const val SECURE_CNAME = "secure_cname"
const val PRIVATE_CDN = "private_cdn"
const val CDN_SUBDOMAIN = "cdn_subdomain"
const val SHORTEN = "shorten"
const val SECURE_CDN_SUBDOMAIN = "secure_cdn_subdomain"
const val USE_ROOT_PATH = "use_root_path"
const val CNAME = "cname"
const val SECURE = "secure"
const val SIGNATURE_ALGORITHM = "signature_algorithm"
const val ANALYTICS = "analytics"

interface IUrlConfig {
    val cname: String?
    @Deprecated(message = "secureDistribution has been deprecated. Please use secureCname instead.", replaceWith = ReplaceWith("secureCname"))
    val secureDistribution: String?
    val secureCname: String?
    val privateCdn: Boolean
    val signUrl: Boolean
    val longUrlSignature: Boolean
    val shorten: Boolean
    val secureCdnSubdomain: Boolean
    val useRootPath: Boolean
    val secure: Boolean
    val forceVersion: Boolean
    val signatureAlgorithm: String?
    val analytics: Boolean
}

data class UrlConfig(
    override val cname: String? = null,
    @Deprecated(
        "secureDistribution has been deprecated. Please use secureCname instead.",
        replaceWith = ReplaceWith("secureCname")
    )
    override val secureDistribution: String? = null,
    override val secureCname: String? = null,
    override val privateCdn: Boolean = DEFAULT_PRIVATE_CDN,
    override val signUrl: Boolean = DEFAULT_SIGN_URL,
    override val longUrlSignature: Boolean = DEFAULT_LONG_URL_SIGNATURE,
    override val shorten: Boolean = DEFAULT_SHORTEN,
    override val secureCdnSubdomain: Boolean = DEFAULT_SECURE_CDN_SUBDOMAIN,
    override val useRootPath: Boolean = DEFAULT_USE_ROOT_PATH,
    override val secure: Boolean = DEFAULT_SECURE,
    override val forceVersion: Boolean = DEFAULT_FORCE_VERSION,
    override val signatureAlgorithm: String = DEFAULT_SIGNATURE_ALGORITHM,
    override val analytics: Boolean = DEFAULT_ANALYTICS
) : IUrlConfig {
    constructor(params: Map<String, Any>) : this(
        cname = params[CNAME]?.toString(),
        secureDistribution = params[SECURE_DISTRIBUTION]?.toString(),
        secureCname = params[SECURE_CNAME]?.toString(),
        privateCdn = params.getBoolean(PRIVATE_CDN) ?: DEFAULT_PRIVATE_CDN,
        signUrl = params.getBoolean(SIGN_URL) ?: DEFAULT_SIGN_URL,
        longUrlSignature = params.getBoolean(LONG_URL_SIGNATURE) ?: DEFAULT_LONG_URL_SIGNATURE,
        shorten = params.getBoolean(SHORTEN) ?: DEFAULT_SHORTEN,
        secureCdnSubdomain = params.getBoolean(SECURE_CDN_SUBDOMAIN) ?: DEFAULT_SECURE_CDN_SUBDOMAIN,
        useRootPath = params.getBoolean(USE_ROOT_PATH) ?: DEFAULT_USE_ROOT_PATH,
        secure = params.getBoolean(SECURE) ?: DEFAULT_SECURE,
        forceVersion = params.getBoolean(FORCE_VERSION) ?: DEFAULT_FORCE_VERSION,
        signatureAlgorithm = params[SIGNATURE_ALGORITHM]?.toString() ?: DEFAULT_SIGNATURE_ALGORITHM,
        analytics = params.getBoolean(ANALYTICS) ?: DEFAULT_ANALYTICS
    )
}