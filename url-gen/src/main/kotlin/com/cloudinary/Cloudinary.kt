package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.util.cloudinaryUrlFromEnv

var instance: Cloudinary? = null
const val SDK_VERSION = "0.0.1-alpha.0"

class Cloudinary(val config: Configuration) {
    private val extensionsLock = Any()
    private val extensions = mutableMapOf<String, Any>()

    constructor(cloudinaryUrl: String) : this(Configuration.fromUri(cloudinaryUrl))
    constructor() : this(
        cloudinaryUrlFromEnv() ?: throw
        IllegalArgumentException("A cloudinary url must be provided")
    )

    val userAgent = "CloudinaryKotlin/$SDK_VERSION"

    fun url(url: (Url.Builder.() -> Unit)? = null): Url {
        val builder = Url.Builder(config)
        url?.let { builder.it() }
        return builder.build()
    }

    /**
     *  ***INTERNAL USE ONLY*** get a custom extensions (property) from this cloudinary instance. A default
     *  must be provided - this method always returns a value.
     */
    fun getExtension(name: String, default: Any): Any {
        return extensions[name] ?: synchronized(extensionsLock) {
            setExtension(name, default)
            default
        }
    }

    /**
     *  ***INTERNAL USE ONLY*** Set a custom extensions (property) on this cloudinary instance.
     */
    fun setExtension(name: String, extension: Any) {
        synchronized(extensionsLock) {
            check(!extensions.containsKey(name)) { "An extension can only be set once" }
            extensions[name] = extension
        }
    }

    companion object {
        /**
         * This flag determines whether to throw an error when a transformation is created with invalid arguments.
         * When false a warning is written to the log and no error is thrown.
         * Default: false.
         */
        var throwOnInvalidTransformations: Boolean = false

        /**
         * Init the singleton instance.
         */
        @Synchronized
        fun init(newInstance: Cloudinary = Cloudinary()) {
            check(instance == null) { "Cloudinary init() must be called only once" }
            instance = newInstance
        }

        /**
         * Get the singleton instance. Note: an explicit call to init() is required before using get().
         */
        fun get(): Cloudinary {
            check(instance != null) { "Cloudinary init() must be called before using singleton instance" }

            // Though this is a var, it is private and cannot be modified from not-null to null (settable only once)
            // so this is a safe unwrapping.
            return instance!!
        }
    }
}
