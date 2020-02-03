package com.cloudinary.util

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

private val RND = SecureRandom()
private val remoteUrlRegex =
    "ftp:.*|https?:.*|s3:.*|gs:.*|data:([\\w-]+/[\\w-]+)?(;[\\w-]+=[\\w-]+)*;base64,([a-zA-Z0-9/+\n=]+)".toRegex()


fun randomPublicId(): String {
    val bytes = ByteArray(8)
    RND.nextBytes(bytes)
    return bytes.toHex()
}

fun apiSignRequest(paramsToSign: MutableMap<String, Any>, apiSecret: String): String {
    val params = ArrayList<String>()
    paramsToSign.entries.forEach {
        val value = if (it.value is List<*>)
            (it.value as Collection<*>).joinToString(",")
        else
            it.value

        params.add("${it.key}=${value}")
    }

    return MessageDigest.getInstance("SHA-1")
        .digest((params.filter { it.isNotBlank() }.sorted().joinToString("&") + apiSecret).toByteArray(Charsets.UTF_8))
        .toHex()
}

fun String.cldIsRemoteUrl() = this.matches(remoteUrlRegex)