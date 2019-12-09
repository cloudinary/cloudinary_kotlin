package com.cloudinary


import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.delivery.Delivery.Companion.format
import com.cloudinary.util.cldHasVersionString
import com.cloudinary.util.cldIsHttpUrl
import com.cloudinary.util.cldMergeSlashedInUrl
import com.cloudinary.util.cldSmartUrlEncode
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

private const val OLD_AKAMAI_SHARED_CDN = "cloudinary-a.akamaihd.net"
private const val AKAMAI_SHARED_CDN = "res.cloudinary.com"
private const val SHARED_CDN = AKAMAI_SHARED_CDN
internal const val DEFAULT_RESOURCE_TYPE = "image"
private const val DEFAULT_TYPE = "upload"


data class Url(
    private val config: Configuration,
    private val cloudName: String = config.cloudName,
    private val publicId: String? = null,
    private val type: String? = null,
    private val resourceType: String = DEFAULT_RESOURCE_TYPE,
    private val format: String? = null,
    private val version: String? = null,
    private val transformation: Transformation? = null,
    private val source: String? = null,
    private val urlSuffix: String? = null,
    private val useRootPath: Boolean = config.useRootPath,
    private val forceVersion: Boolean = true,
    private val secureDistribution: String? = config.secureDistribution,
    private val privateCdn: Boolean = config.privateCdn,
    private val shorten: Boolean = config.shorten
) {
    fun generate(source: String? = null): String? {
        var computedSource: String = source ?: this.source ?: this.publicId ?: return null

        val isHttpSource = computedSource.cldIsHttpUrl()
        if (isHttpSource && (type == "asset" || type.isNullOrBlank())) {
            return computedSource
        }

        var transformation = this.transformation
        var format = this.format
        if (type == "fetch" && !format.isNullOrBlank()) {
            transformation = (transformation ?: Transformation()).delivery(format(format))
            format = null
        }

        val finalizedSource = finalizeSource(computedSource, format, urlSuffix)
        computedSource = finalizedSource.first
        val sourceToSign = finalizedSource.second

        var version =
            if (forceVersion && sourceToSign.contains("/") && !sourceToSign.cldHasVersionString() &&
                !isHttpSource && version.isNullOrBlank()
            ) "1" else ""

        if (version != "") version = "v$version"

        val finalResourceType = finalizeResourceType()
        val prefix = unsignedDownloadUrlPrefix()

        return listOfNotNull(prefix, finalResourceType, transformation, version, computedSource).joinToString("/")
            .cldMergeSlashedInUrl()
    }

    private fun finalizeSource(source: String, format: String?, urlSuffix: String?): Pair<String, String> {
        var computedSource = source
        computedSource = computedSource.cldMergeSlashedInUrl()
        var sourceToSign: String
        if (computedSource.cldIsHttpUrl()) {
            computedSource = computedSource.cldSmartUrlEncode()
            sourceToSign = computedSource
        } else {
            try {
                computedSource = URLDecoder.decode(computedSource.replace("+", "%2B"), "UTF-8").cldSmartUrlEncode()
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException(e)
            }

            sourceToSign = computedSource
            if (!urlSuffix.isNullOrBlank()) {
                require(!urlSuffix.run { contains(".") || contains("/") }) { "url_suffix should not include . or /" }
                computedSource = "$computedSource/$urlSuffix"
            }
            if (!format.isNullOrBlank()) {
                computedSource = "$computedSource.$format"
                sourceToSign = "$sourceToSign.$format"
            }
        }

        return Pair(computedSource, sourceToSign)
    }

    private fun finalizeResourceType(): String? {
        var computedResourceType: String? = resourceType

        var computedType = type
        if (computedType == null) {
            computedType = DEFAULT_TYPE
        }

        if (!urlSuffix.isNullOrBlank()) {
            if (computedResourceType == "image" && computedType == "upload") {
                computedResourceType = "images"
                computedType = null
            } else if (computedResourceType == "image" && computedType == "private") {
                computedResourceType = "private_images"
                computedType = null
            } else if (computedResourceType == "image" && computedType == "authenticated") {
                computedResourceType = "authenticated_images"
                computedType = null
            } else if (computedResourceType == "raw" && computedType == "upload") {
                computedResourceType = "files"
                computedType = null
            } else if (computedResourceType == "video" && computedType == "upload") {
                computedResourceType = "videos"
                computedType = null
            } else {
                throw IllegalArgumentException("URL Suffix only supported for image/upload, image/private, raw/upload, image/authenticated  and video/upload")
            }
        }
        if (useRootPath) {
            if (computedResourceType == "image" && computedType == "upload" || computedResourceType == "images" && computedType.isNullOrBlank()) {
                computedResourceType = null
                computedType = null
            } else {
                throw IllegalArgumentException("Root path only supported for image/upload")
            }
        }
        if (shorten && computedResourceType == "image" && computedType == "upload") {
            computedResourceType = "iu"
            computedType = null
        }
        var result = computedResourceType
        if (computedType != null) {
            result += "/$computedType"
        }
        return result
    }

    private fun unsignedDownloadUrlPrefix(): String {
        var localSecureDistribution: String? = secureDistribution
        if (cloudName.startsWith("/")) {
            return "/res$cloudName"
        }
        var sharedDomain = !privateCdn

        if (localSecureDistribution.isNullOrBlank() || localSecureDistribution == OLD_AKAMAI_SHARED_CDN) {
            localSecureDistribution =
                if (privateCdn) "$cloudName-res.cloudinary.com" else SHARED_CDN
        }

        if (!sharedDomain) {
            sharedDomain = localSecureDistribution == SHARED_CDN
        }

        var prefix = "https://$localSecureDistribution"

        if (sharedDomain) {
            prefix += "/$cloudName"
        }

        return prefix
    }
}

