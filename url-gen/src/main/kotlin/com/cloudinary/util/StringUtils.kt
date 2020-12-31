package com.cloudinary.util

import com.cloudinary.util.Base64Coder.encodeString
import com.cloudinary.util.Base64Coder.encodeURLSafeString
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * If the param is null the original string is returned unchanged.
 */
internal fun String.cldJoinWithOrReturnOriginal(separator: String, toJoin: Any?): String {
    return if (toJoin != null) this + separator + toJoin.toString() else this
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
        inVersion = if (c == 'v') {
            true
        } else if (Character.isDigit(c) && inVersion) {
            return true
        } else {
            false
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
                inMerge = if (prevIsColon) {
                    builder.append(c)
                    false
                } else {
                    if (!inMerge) {
                        builder.append(c)
                    }
                    true
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


/**
 *
 */
internal fun URI.cldQueryAsMap() =
    query?.split("&")?.associate { it.split("=").run { Pair(this[0], this[1]) } } ?: emptyMap()

/**
 * Returns the base64 representation of this string
 */
internal fun String.cldToBase64(): String = encodeString(this)

/**
 * Returns the url-safe variant of the base64 representation of this string
 */
internal fun String.cldToUrlSafeBase64() = encodeURLSafeString(this)

/**
 * Encodes public id to be used in urls (such as wasm asset or layers)
 */
internal fun String.cldEncodePublicId() = replace('/', ':')

/**
 * Replaces the unsafe characters in url with url-encoded values.
 * This is based on [java.net.URLEncoder.encode]
 * @param unsafe Regex pattern of unsafe caracters
 * @param charset
 * @return An encoded url string
 */
internal fun String.cldUrlEncode(unsafe: Pattern, charset: Charset?): String {
    val sb = StringBuffer(length)
    val matcher = unsafe.matcher(this)
    while (matcher.find()) {
        val str = matcher.group(0)
        val bytes = str.toByteArray(charset!!)
        val escaped = java.lang.StringBuilder(str.length * 3)
        for (aByte in bytes) {
            escaped.append('%')
            var ch = Character.forDigit(((aByte.toInt()) shr 4 and 0xF), 16)
            escaped.append(ch)
            ch = Character.forDigit((aByte.toInt() and 0xF), 16)
            escaped.append(ch)
        }
        matcher.appendReplacement(sb, Matcher.quoteReplacement(escaped.toString().toLowerCase(Locale.US)))
    }
    matcher.appendTail(sb)
    return sb.toString()
}

internal fun String.cldHexStringToByteArray(): ByteArray {
    val len: Int = this.length
    val data = ByteArray(len / 2)

    require(len % 2 == 0) { "Length of string to parse must be even." }

    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(get(i), 16) shl 4) + Character.digit(
            get(i + 1),
            16
        )).toByte()
        i += 2
    }

    return data
}

private val HEX_ARRAY = "0123456789abcdef".toCharArray()
private val camelCaseRegex = Regex("""[A-Z]""")

fun ByteArray.toHex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v = get(j).toInt() and 0xFF
        hexChars[j * 2] = HEX_ARRAY[v.ushr(4)]
        hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
    }

    return String(hexChars)
}

/**
 * Remove all consecutive chars c from the beginning of the string
 * @param c Char to search for
 * @return The string stripped from the starting chars.
 */
fun String.cldRemoveStartingChars(c: Char): String {
    var lastToRemove = -1
    for (i in indices) {
        if (this[i] == c) {
            lastToRemove = i
            continue
        }
        if (this[i] != c) {
            break
        }
    }
    return if (lastToRemove < 0) this else substring(lastToRemove + 1)
}