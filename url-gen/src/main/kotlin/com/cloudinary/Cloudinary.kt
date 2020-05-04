package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.util.cloudinaryUrlFromEnv

class Cloudinary(val config: Configuration) {
    constructor(cloudinaryUrl: String) : this(Configuration.fromUri(cloudinaryUrl))
    constructor() : this(
        cloudinaryUrlFromEnv() ?: throw
        IllegalArgumentException("A cloudinary url must be provided")
    )

    private val sdkVersion = "1.0.0"
    val userAgent = "CloudinaryKotlin/$sdkVersion"

    fun url(url: (Url.Builder.() -> Unit)? = null): Url {
        val builder = Url.Builder(config)
        url?.let { builder.it() }
        return builder.build()
    }
}
