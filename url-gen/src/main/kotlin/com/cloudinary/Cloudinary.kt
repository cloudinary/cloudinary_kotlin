package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.util.cloudinaryUrlFromEnv

var instance: Cloudinary? = null

private val initLockObject = Any()

class Cloudinary(val config: Configuration) {
    private val extensionsLock = Any()
    private val extensions = mutableMapOf<String, Any>()

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
         * Init the singleton instance.
         */
        fun init(newInstance: Cloudinary = Cloudinary()) {
            synchronized(initLockObject) {
                if (instance == null) throw Error(IllegalStateException("Cloudinary init() must be called only once"))
                instance = newInstance
            }
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
