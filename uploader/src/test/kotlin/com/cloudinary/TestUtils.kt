package com.cloudinary

import com.cloudinary.http.HttpResponse
import com.cloudinary.http.MultipartEntityImpl
import com.cloudinary.util.Base64Coder
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun assertMapsHaveSameEntries(expected: Map<*, *>, actual: Map<*, *>?) {
    assertNotNull(actual)
    assertEquals(expected.size, actual.size)

    expected.forEach { assertEquals(it.value, actual[it.key]) }
}

fun generateFile(): File {
    val temp = File.createTempFile("cldupload.test.", "")
    val out = FileOutputStream(temp)
    val header = intArrayOf(
        0x42,
        0x4D,
        0x4A,
        0xB9,
        0x59,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x8A,
        0x00,
        0x00,
        0x00,
        0x7C,
        0x00,
        0x00,
        0x00,
        0x78,
        0x05,
        0x00,
        0x00,
        0x78,
        0x05,
        0x00,
        0x00,
        0x01,
        0x00,
        0x18,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0xC0,
        0xB8,
        0x59,
        0x00,
        0x61,
        0x0F,
        0x00,
        0x00,
        0x61,
        0x0F,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0xFF,
        0x00,
        0x00,
        0xFF,
        0x00,
        0x00,
        0xFF,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0xFF,
        0x42,
        0x47,
        0x52,
        0x73,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x54,
        0xB8,
        0x1E,
        0xFC,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x66,
        0x66,
        0x66,
        0xFC,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0xC4,
        0xF5,
        0x28,
        0xFF,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x04,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00
    )
    val byteHeader = ByteArray(138)
    for (i in 0..137) byteHeader[i] = header[i].toByte()
    val piece = ByteArray(10)
    Arrays.fill(piece, 0xff.toByte())
    out.write(byteHeader)
    for (i in 1..588000) {
        out.write(piece)
    }
    out.close()
    return temp
}

fun createUploadPreset(cloudinary: Cloudinary, preset: String): HttpResponse? {
    val apiUrl =
        "${cloudinary.config.uploadPrefix ?: "https://api.cloudinary.com"}/v1_1/${cloudinary.config.cloudName}/upload_presets?signed=true"

    return cloudinary.httpClientFactory.getClient().post(
        URL(apiUrl),
        mapOf("Authorization" to "Basic " + Base64Coder.encodeString(cloudinary.config.apiKey + ":" + cloudinary.config.apiSecret)),
        MultipartEntityImpl()
    )
}

fun doResourcesHaveTag(cloudinary: Cloudinary, tag: String, vararg publicIds: String): Boolean {
    val apiUrl =
        "${cloudinary.config.uploadPrefix ?: "https://api.cloudinary.com"}/v1_1/${cloudinary.config.cloudName}/resources/image/tags/$tag"
    val res = cloudinary.httpClientFactory.getClient().get(
        URL(apiUrl),
        mapOf("Authorization" to "Basic " + Base64Coder.encodeString(cloudinary.config.apiKey + ":" + cloudinary.config.apiSecret))
    )

    if (res?.content == null) {
        throw Error("Could not verify tags: " + res?.httpStatusCode)
    } else {
        val resultBody = res.content!!
        for (id: String in publicIds) {
            if (!resultBody.contains("\"public_id\":\"$id\"")) return false
        }
    }

    return true
}