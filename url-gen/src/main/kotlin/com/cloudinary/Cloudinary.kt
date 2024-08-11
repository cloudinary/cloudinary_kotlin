package com.cloudinary

import com.cloudinary.asset.Asset
import com.cloudinary.asset.Image
import com.cloudinary.asset.Video
import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.util.cloudinaryUrlFromEnv

private var instance: Cloudinary? = null

class Cloudinary(val config: CloudinaryConfig) {
    private val extensionsLock = Any()
    private val extensions = mutableMapOf<String, Any>()

    constructor(cloudinaryUrl: String) : this(CloudinaryConfig.fromUri(cloudinaryUrl))
    constructor() : this(
        cloudinaryUrlFromEnv() ?: throw
        IllegalArgumentException("A cloudinary url must be provided")
    )

    val userAgent = "CloudinaryKotlin/$SDK_VERSION"

    fun raw(options: (Asset.Builder.() -> Unit)? = null): Asset {
        val builder = Asset.Builder(config.cloudConfig, config.urlConfig, "raw")
        options?.let { builder.it() }
        return builder.build()
    }

    fun image(options: (Image.Builder.() -> Unit)? = null): Image {
        val builder = Image.Builder(config.cloudConfig, config.urlConfig)
        options?.let { builder.it() }
        return builder.build()
    }

    fun video(options: (Video.Builder.() -> Unit)? = null): Video {
        val builder = Video.Builder(config.cloudConfig, config.urlConfig)
        options?.let { builder.it() }
        return builder.build()
    }

    /**
     * @suppress
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
     * * @suppress
     *  ***INTERNAL USE ONLY*** Set a custom extensions (property) on this cloudinary instance.
     */
    fun setExtension(name: String, extension: Any) {
        synchronized(extensionsLock) {
            check(!extensions.containsKey(name)) { "An extension can only be set once" }
            extensions[name] = extension
        }
    }

    companion object {
        const val SDK_VERSION = "1.10.0"

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
