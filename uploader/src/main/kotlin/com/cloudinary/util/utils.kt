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


fun apiSignRequest(paramsToSign: MutableMap<String, Any>, apiSecret: String): String {
    val params = ArrayList<String>()

    paramsToSign.entries.forEach {
        val rawValue = if (it.value is List<*>) {
            (it.value as Collection<*>).joinToString(",")
        } else {
            it.value.toString()
        }

        // Escape '&' in the value
        val escapedValue = rawValue.replace("&", "%26")

        params.add("${it.key}=$escapedValue")
    }

    val toSign = params.filter { it.isNotBlank() }
        .sorted()
        .joinToString("&") + apiSecret

    return MessageDigest.getInstance("SHA-1")
        .digest(toSign.toByteArray(Charsets.UTF_8))
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