package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Transformation
import com.cloudinary.util.cloudinaryUrlFromEnv

class Cloudinary(internal val config: Configuration) {
    constructor(cloudinaryUrl: String) : this(Configuration.fromUri(cloudinaryUrl))
    constructor() : this(
        cloudinaryUrlFromEnv() ?: throw
        IllegalArgumentException("A cloudinary url must be provided")
    )

    fun url(
        cloudName: String = config.cloudName,
        publicId: String? = null,
        type: String? = null,
        resourceType: String = DEFAULT_RESOURCE_TYPE,
        format: String? = null,
        version: String? = null,
        transformation: Transformation? = null,
        source: String? = null,
        urlSuffix: String? = null,
        useRootPath: Boolean = config.useRootPath,
        forceVersion: Boolean = true,
        secureDistribution: String? = config.secureDistribution,
        privateCdn: Boolean = config.privateCdn,
        shorten: Boolean = config.shorten
    ) = Url(
        config,
        cloudName,
        publicId,
        type,
        resourceType,
        format,
        version,
        transformation,
        source,
        urlSuffix,
        useRootPath,
        forceVersion,
        secureDistribution,
        privateCdn,
        shorten
    )
}
