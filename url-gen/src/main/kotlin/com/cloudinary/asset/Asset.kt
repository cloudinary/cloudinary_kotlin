package com.cloudinary.asset


import com.cloudinary.NULL_AUTH_TOKEN
import com.cloudinary.config.CloudConfig
import com.cloudinary.config.UrlConfig
import com.cloudinary.generateAnalyticsSignature
import com.cloudinary.transformation.*
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
internal const val DEFAULT_ASSET_TYPE = "image"
internal const val DEFAULT_DELIVERY_TYPE = "upload"

const val ASSET_TYPE_IMAGE = "image"
const val ASSET_TYPE_VIDEO = "video"

class Asset(
    // config
    cloudConfig: CloudConfig,
    urlConfig: UrlConfig,

    // fields
    version: String? = null,
    publicId: String? = null,
    extension: Format? = null,
    urlSuffix: String? = null,
    assetType: String = DEFAULT_ASSET_TYPE,
    storageType: String? = null,
    private val transformation: Transformation? = null
) : BaseAsset(
    cloudConfig,
    urlConfig,
    version,
    publicId,
    extension,
    urlSuffix,
    assetType,
    storageType
) {

    override fun getTransformationString() = transformation?.toString()

    class Builder(cloudConfig: CloudConfig, urlConfig: UrlConfig, assetType: String = DEFAULT_ASSET_TYPE) :
        BaseAssetBuilder(cloudConfig, urlConfig, assetType), ITransformable<Builder> {

        private var transformation: Transformation? = null

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transform: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transform()
            this.transformation = builder.build()
        }

        override fun add(action: Action) = apply {
            this.transformation = (this.transformation ?: Transformation()).add(action)
        }

        fun build() = Asset(
            cloudConfig,
            urlConfig,
            version,
            publicId,
            extension,
            urlSuffix,
            assetType,
            storageType,
            transformation
        )
    }
}

@TransformationDsl
abstract class BaseAsset constructor(
    // config
    private val cloudConfig: CloudConfig,
    private val urlConfig: UrlConfig,

    // fields
    private val version: String? = null,
    private val publicId: String? = null,
    private val extension: Format? = null,
    private val urlSuffix: String? = null,
    private val assetType: String = DEFAULT_ASSET_TYPE,
    private val storageType: String? = null
) {
    fun generate(source: String? = null): String? {
        require(cloudConfig.cloudName.isNotBlank()) { "Must supply cloud_name in configuration" }

        var mutableSource = source ?: publicId ?: return null

        val httpSource = mutableSource.cldIsHttpUrl()

        if (httpSource && (storageType.isNullOrBlank() || storageType == "asset")) {
            return mutableSource
        }

        var signature = ""

        val finalizedSource =
            finalizeSource(mutableSource, extension, urlSuffix)

        mutableSource = finalizedSource.source
        val sourceToSign = finalizedSource.sourceToSign

        var mutableVersion = version
        if (urlConfig.forceVersion && sourceToSign.contains("/") && !sourceToSign.cldHasVersionString() &&
            !httpSource && mutableVersion.isNullOrBlank()
        ) {
            mutableVersion = "1"
        }

        mutableVersion = if (mutableVersion == null) "" else "v$mutableVersion"

        val transformationString = getTransformationString()
        if (urlConfig.signUrl && (cloudConfig.authToken == null || cloudConfig.authToken == NULL_AUTH_TOKEN)) {
            val signatureAlgorithm = if (urlConfig.longUrlSignature) "SHA-256" else urlConfig.signatureAlgorithm


            val toSign = listOfNotNull(transformationString, sourceToSign)
                .joinToString("/")
                .cldRemoveStartingChars('/')
                .cldMergeSlashedInUrl()

            val hash = hash(toSign + cloudConfig.apiSecret, signatureAlgorithm)
            signature = Base64Coder.encodeURLSafeString(hash)
            signature = "s--" + signature.substring(0, if (urlConfig.longUrlSignature) 32 else 8) + "--"
        }

        val finalizedResourceType = finalizeResourceType(
            assetType,
            storageType,
            urlSuffix,
            urlConfig.useRootPath,
            urlConfig.shorten
        )

        val prefix = unsignedDownloadUrlPrefix(
            cloudConfig.cloudName,
            urlConfig.privateCdn,
            urlConfig.cname,
            urlConfig.secure,
            urlConfig.secureDistribution
        )

        val url =
            listOfNotNull(
                prefix,
                finalizedResourceType,
                signature,
                transformationString,
                mutableVersion,
                mutableSource
            ).joinToString("/").cldMergeSlashedInUrl()

        val analytics = if (urlConfig.analytics) "_a=${generateAnalyticsSignature()}" else null

        return if (urlConfig.signUrl && cloudConfig.authToken != null && cloudConfig.authToken != NULL_AUTH_TOKEN) {
            val token = cloudConfig.authToken.generate(URL(url).path)
            "$url?$token".joinWithValues(analytics, separator = "&")
        } else {
            url.joinWithValues(analytics, separator = "?")
        }
    }

    abstract fun getTransformationString(): String?

    @TransformationDsl
    abstract class BaseAssetBuilder
    internal constructor(
        protected val cloudConfig: CloudConfig,
        protected val urlConfig: UrlConfig,
        protected var assetType: String = DEFAULT_ASSET_TYPE
    ) {

        protected var version: String? = null
        protected var publicId: String? = null
        protected var extension: Format? = null
        protected var urlSuffix: String? = null

        var storageType: String? = null

        fun version(version: String) = apply { this.version = version }
        fun publicId(publicId: String) = apply { this.publicId = publicId }
        fun extension(extension: Format) = apply { this.extension = extension }
        fun urlSuffix(urlSuffix: String) = apply { this.urlSuffix = urlSuffix }
        fun storageType(storageType: String) = apply { this.storageType = storageType }
    }
}

private fun finalizeSource(
    source: String,
    extension: Format?,
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
        if (extension != null) {
            mutableSource = "$mutableSource.$extension"
            sourceToSign = "$sourceToSign.$extension"
        }
    }

    return FinalizedSource(mutableSource, sourceToSign)
}

private fun finalizeResourceType(
    resourceType: String?,
    type: String?,
    urlSuffix: String?,
    useRootPath: Boolean,
    shorten: Boolean
): String? {
    var mutableResourceType: String? = resourceType ?: DEFAULT_ASSET_TYPE
    var mutableType: String? = type ?: DEFAULT_DELIVERY_TYPE

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

private fun unsignedDownloadUrlPrefix(
    cloudName: String?,
    privateCdn: Boolean,
    cname: String?,
    secure: Boolean,
    secureDistribution: String?
): String {
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

/**
 * Computes hash from input string using specified algorithm.
 *
 * @param input              string which to compute hash from
 * @param signatureAlgorithm algorithm to use for computing hash (supports only SHA-1 and SHA-256)
 * @return array of bytes of computed hash value
 */
private fun hash(input: String, signatureAlgorithm: String): ByteArray? {
    return try {
        MessageDigest.getInstance(signatureAlgorithm).digest(input.toByteArray(Charset.forName("UTF-8")))
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException("Unexpected exception", e)
    }
}