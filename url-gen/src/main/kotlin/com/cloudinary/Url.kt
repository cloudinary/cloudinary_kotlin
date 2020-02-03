package com.cloudinary


import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.delivery.Delivery.Companion.format
import com.cloudinary.util.*
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private const val OLD_AKAMAI_SHARED_CDN = "cloudinary-a.akamaihd.net"
private const val AKAMAI_SHARED_CDN = "res.cloudinary.com"
private const val SHARED_CDN = AKAMAI_SHARED_CDN
internal const val DEFAULT_RESOURCE_TYPE = "image"
private const val DEFAULT_TYPE = "upload"


data class Url(
    private val config: Configuration,
    val cloudName: String = config.cloudName,
    private val publicId: String? = null,
    val type: String? = null,
    val resourceType: String = DEFAULT_RESOURCE_TYPE,
    private val format: String? = null,
    val version: String? = null,
    val transformation: Transformation? = null,
    private val signUrl: Boolean = false,
    val authToken: AuthToken? = config.authToken,
    private val source: String? = null,
    private val urlSuffix: String? = null,
    val useRootPath: Boolean = config.useRootPath,
    val forceVersion: Boolean = true,
    private val secureDistribution: String? = config.secureDistribution,
    val privateCdn: Boolean = config.privateCdn,
    val shorten: Boolean = config.shorten,
    val secure: Boolean = config.secure,
    val cname: String? = config.cname
) {

    fun generate(source: String? = null): String? {
        require(cloudName.isNotBlank()) { "Must supply cloud_name in configuration" }
        var mutableSource = source ?: publicId ?: this.source ?: return null
        var mutableTransformation = transformation ?: Transformation()
        var mutableFormat = format

        val httpSource = mutableSource.cldIsHttpUrl()

        if (httpSource && (type.isNullOrBlank() || type == "asset")) {
            return mutableSource
        }

        if (type == "fetch" && !mutableFormat.isNullOrBlank()) {
            mutableTransformation = mutableTransformation.delivery(format(mutableFormat))
            mutableFormat = null
        }

        val transformationStr = mutableTransformation.toString()
        var signature = ""

        val finalizedSource = finalizeSource(mutableSource, mutableFormat, urlSuffix)
        mutableSource = finalizedSource.source
        val sourceToSign = finalizedSource.sourceToSign

        var mutableVersion = version
        if (forceVersion && sourceToSign.contains("/") && !sourceToSign.cldHasVersionString() &&
            !httpSource && mutableVersion.isNullOrBlank()
        ) {
            mutableVersion = "1"
        }

        mutableVersion = if (mutableVersion == null) "" else "v$mutableVersion"

        if (signUrl && (authToken == null || authToken == NULL_AUTH_TOKEN)) {
            val md: MessageDigest?
            md = try {
                MessageDigest.getInstance("SHA-1")
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Unexpected exception", e)
            }

            val toSign = listOf(transformationStr, sourceToSign)
                .joinToString("/")
                .cldRemoveStartingChars('/')
                .cldMergeSlashedInUrl()
            val digest = md.digest((toSign + config.apiSecret!!).toByteArray(Charset.forName("UTF-8")))
            signature = Base64Coder.encodeURLSafeString(digest)
            signature = "s--" + signature.substring(0, 8) + "--"
        }

        val finalizedResourceType = finalizeResourceType(resourceType, type, urlSuffix, useRootPath, shorten)

        val prefix = unsignedDownloadUrlPrefix2(
            cloudName,
            privateCdn,
            cname,
            secure,
            secureDistribution
        )

        val url =
            listOfNotNull(
                prefix,
                finalizedResourceType,
                signature,
                transformationStr,
                mutableVersion,
                mutableSource
            ).joinToString("/").cldMergeSlashedInUrl()

        return if (signUrl && authToken != null && authToken != NULL_AUTH_TOKEN) {
            val token = authToken.generate(URL(url).path)
            "$url?$token"
        } else {
            url
        }
    }
}

private fun finalizeSource(
    source: String,
    format: String?,
    urlSuffix: String?
): FinalizedSource {
    var mutableSource = source.cldMergeSlashedInUrl()
    var sourceToSign: String
    if (mutableSource.cldIsHttpUrl()) {
        mutableSource = mutableSource.cldSmartUrlEncode()
        sourceToSign = mutableSource
    } else {
        mutableSource = try {
            URLDecoder.decode(mutableSource.replace("+", "%2B"), "UTF-8").cldSmartUrlEncode()
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
        sourceToSign = mutableSource
        if (!urlSuffix.isNullOrBlank()) {
            require(!(urlSuffix.contains(".") || urlSuffix.contains("/"))) { "url_suffix should not include . or /" }
            mutableSource = "$mutableSource/$urlSuffix"
        }
        if (!format.isNullOrBlank()) {
            mutableSource = "$mutableSource.$format"
            sourceToSign = "$sourceToSign.$format"
        }
    }

    return FinalizedSource(mutableSource, sourceToSign)
}

fun finalizeResourceType(
    resourceType: String?,
    type: String?,
    urlSuffix: String?,
    useRootPath: Boolean,
    shorten: Boolean
): String? {
    var mutableResourceType: String? = resourceType ?: "image"
    var mutableType: String? = type ?: "upload"

    if (!urlSuffix.isNullOrBlank()) {
        if (mutableResourceType == "image" && mutableType == "upload") {
            mutableResourceType = "images"
            mutableType = null
        } else if (mutableResourceType == "image" && mutableType == "private") {
            mutableResourceType = "private_images"
            mutableType = null
        } else if (mutableResourceType == "image" && mutableType == "authenticated") {
            mutableResourceType = "authenticated_images"
            mutableType = null
        } else if (mutableResourceType == "raw" && mutableType == "upload") {
            mutableResourceType = "files"
            mutableType = null
        } else if (mutableResourceType == "video" && mutableType == "upload") {
            mutableResourceType = "videos"
            mutableType = null
        } else {
            throw IllegalArgumentException("URL Suffix only supported for image/upload, image/private, raw/upload, image/authenticated  and video/upload")
        }
    }
    if (useRootPath) {
        if (mutableResourceType == "image" && mutableType == "upload" || mutableResourceType == "images" && mutableType.isNullOrBlank()) {
            mutableResourceType = null
            mutableType = null
        } else {
            throw IllegalArgumentException("Root path only supported for image/upload")
        }
    }
    if (shorten && mutableResourceType == "image" && mutableType == "upload") {
        mutableResourceType = "iu"
        mutableType = null
    }
    var result = mutableResourceType
    if (mutableType != null) {
        result += "/$mutableType"
    }

    return result
}

fun unsignedDownloadUrlPrefix2(
    cloudName: String?,
    privateCdn: Boolean,
    cname: String?,
    secure: Boolean,
    secureDistribution: String?
): String? {
    var mutableCloudName = cloudName
    var mutableSecureDistribution = secureDistribution
    mutableCloudName?.let {
        if (it.startsWith("/")) return "/res$mutableCloudName"
    }

    var sharedDomain: Boolean = !privateCdn
    var prefix: String
    if (secure) {
        if (mutableSecureDistribution.isNullOrBlank() || mutableSecureDistribution == OLD_AKAMAI_SHARED_CDN) {
            mutableSecureDistribution =
                if (privateCdn) mutableCloudName.toString() + "-res.cloudinary.com" else SHARED_CDN
        }
        if (!sharedDomain) {
            sharedDomain = mutableSecureDistribution == SHARED_CDN
        }

        prefix = "https://$mutableSecureDistribution"
    } else if (!cname.isNullOrBlank()) {
        prefix = "http://$cname"
    } else {
        val protocol = "http://"
        mutableCloudName = if (privateCdn) "$mutableCloudName-" else ""
        val res = "res"
        val domain = ".cloudinary.com"
        prefix = protocol + mutableCloudName + res + domain
    }
    if (sharedDomain) {
        // use original cloud name here:
        prefix += "/$cloudName"
    }

    return prefix
}

private class FinalizedSource(val source: String, val sourceToSign: String)
