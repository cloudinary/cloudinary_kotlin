package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Transformation
import com.cloudinary.util.cloudinaryUrlFromEnv

class Cloudinary(val config: Configuration) {
    constructor(cloudinaryUrl: String) : this(Configuration.fromUri(cloudinaryUrl))
    constructor() : this(
        cloudinaryUrlFromEnv() ?: throw
        IllegalArgumentException("A cloudinary url must be provided")
    )

    private val sdkVersion = "1.0.0"
    val userAgent = "CloudinaryKotlin/$sdkVersion"

    fun url(
        cloudName: String = config.cloudName,
        publicId: String? = null,
        type: String? = null,
        resourceType: String = DEFAULT_RESOURCE_TYPE,
        format: String? = null,
        version: String? = null,
        transformation: Transformation? = null,
        signUrl: Boolean = false,
        authToken: AuthToken? = config.authToken,
        source: String? = null,
        urlSuffix: String? = null,
        useRootPath: Boolean = config.useRootPath,
        forceVersion: Boolean = true,
        secureDistribution: String? = config.secureDistribution,
        privateCdn: Boolean = config.privateCdn,
        shorten: Boolean = config.shorten,
        secure: Boolean = config.secure,
        cname: String? = config.cname
    ) = Url(
        config,
        cloudName,
        publicId,
        type,
        resourceType,
        format,
        version,
        transformation,
        signUrl,
        authToken,
        source,
        urlSuffix,
        useRootPath,
        forceVersion,
        secureDistribution,
        privateCdn,
        shorten,
        secure,
        cname
    )
}
