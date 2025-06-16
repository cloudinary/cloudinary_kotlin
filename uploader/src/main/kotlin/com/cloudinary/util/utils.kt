package com.cloudinary.util

import java.io.File
import java.io.InputStream
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

fun apiSignRequest(paramsToSign: Map<String, Any>, apiSecret: String): String {
    val queryString = paramsToSign.entries
        .map { "${it.key}=${(if (it.value is List<*>) (it.value as Collection<*>).joinToString(",") else it.value.toString()).replace("&", "%26")}" }
        .filter { it.isNotBlank() }
        .sorted()
        .joinToString("&")

    return MessageDigest.getInstance("SHA-1")
        .digest((queryString + apiSecret).toByteArray(Charsets.UTF_8))
        .toHex()
}

fun String.cldIsRemoteUrl() = this.matches(remoteUrlRegex)

fun InputStream.splitToFiles(chunkSize: Int, targetPath: File): List<File> {
    val result = mutableListOf<File>()

    use { input ->
        // TODO we should probably use a smaller buffer, chunk size can be 20[MB] or more.
        val buffer = ByteArray(chunkSize)
        var bytesRead: Int
        var offset = 0
        var partIndex = 1

        do {
            bytesRead = input.read(buffer, 0, chunkSize)

            if (bytesRead == -1) break

            offset += bytesRead
            val indexStr = partIndex.toString().padStart(4, '0')
            val file = File("$targetPath${File.separator}part_${indexStr}")
            file.createNewFile()
            if (bytesRead < buffer.size) {
                // last chunk
                file.writeBytes(buffer.sliceArray(0 until bytesRead))
            } else {
                file.writeBytes(buffer)
            }

            result.add(file)
            partIndex++
        } while (bytesRead > 0)
    }

    return result
}