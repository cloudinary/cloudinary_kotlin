package com.cloudinary.util

/**
 * Retrieves the cloudinary url from project properties, with fallback to environment variables.
 */
internal fun cloudinaryUrlFromEnv(): String? = System.getProperty("CLOUDINARY_URL", System.getenv("CLOUDINARY_URL"))