package com.cloudinary.util

import java.net.URI
import java.net.URLEncoder
import java.util.*

internal fun Any.cldToString(): String {
    return when (this) {
        is Int -> toString()
        is Float -> toString()
        else -> toString()
    }
}

/**
 * Returns this string prepended with prefix.
 */
internal fun Any.cldPrepend(prefix: String) = prefix + this

/**
 * If the param is null the original string is returned unchanged.
 */
internal fun String.cldJoin(separator: String, toJoin: Any?): String? {
    return if (toJoin != null) this + separator + toJoin.cldToString() else this
}

/**
 * Checks whether this string is a valid http url.
 */
internal fun String.cldIsHttpUrl() = startsWith("https:/", true) || startsWith("http:/", true)

/**
 * Checks whether this url string has a version component in it (e.g. 'v12345')
 */
internal fun String.cldHasVersionString(): Boolean {
    var inVersion = false
    for (i in 0 until length) {
        val c = get(i)
        if (c == 'v') {
            inVersion = true
        } else if (Character.isDigit(c) && inVersion) {
            return true
        } else {
            inVersion = false
        }
    }

    return false
}

/**
 * Returns this string with all duplicate slashes merged into a single slash (e.g. "abc///efg -> "abc/efg")
 */
internal fun String.cldMergeSlashedInUrl(): String {
    val builder = StringBuilder()
    var prevIsColon = false
    var inMerge = false
    for (i in 0 until length) {
        val c = get(i)
        if (c == ':') {
            prevIsColon = true
            builder.append(c)
        } else {
            if (c == '/') {
                if (prevIsColon) {
                    builder.append(c)
                    inMerge = false
                } else {
                    if (!inMerge) {
                        builder.append(c)
                    }
                    inMerge = true
                }
            } else {
                inMerge = false
                builder.append(c)
            }

            prevIsColon = false
        }
    }

    return builder.toString()
}

/**
 * Merge all consecutive underscores and spaces into a single underscore, e.g. "ab___c_  _d" becomes "ab_c_d"
 */
internal fun String.cldMergeToSingleUnderscore(): String {
    val buffer = StringBuffer()
    var inMerge = false
    for (i in 0 until length) {
        val c = this[i]
        if (c == ' ' || c == '_') {
            if (!inMerge) {
                buffer.append('_')
            }
            inMerge = true

        } else {
            inMerge = false
            buffer.append(c)
        }
    }

    return buffer.toString()
}

/**
 * Encodes the url for Cloudinary use (standard url encoding + some custom replacements).
 */
internal fun String.cldSmartUrlEncode() = URLEncoder.encode(this, "UTF-8")
    .replace("%2F", "/")
    .replace("%3A", ":")
    .replace("+", "%20")

internal fun String.cldRemovePound() = replaceFirst("#", "")

/**
 *
 */
internal fun URI.cldQueryAsMap() =
    query?.split("&")?.associate { it.split("=").run { Pair(this[0], this[1]) } } ?: emptyMap()

// TODO - this implementation is java 8 only??
/**
 * Returns the base64 representation of this string
 */
internal fun String.cldToBase64(): String = Base64.getEncoder().encodeToString(this.toByteArray(Charsets.UTF_8))

/**
 * Returns the url-safe variant of the base64 representation of this string
 */
internal fun String.cldToUrlSafeBase64() = Base64.getUrlEncoder().encodeToString(this.toByteArray(Charsets.UTF_8))

/**
 * Encodes public id to be used in urls (such as wasm asset or layers)
 */
internal fun String.cldEncodePublicId() = replace('/', ':')