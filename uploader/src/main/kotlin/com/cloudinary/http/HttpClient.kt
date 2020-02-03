package com.cloudinary.http

import java.net.URL

typealias ProgressCallback = ((bytesUploaded: Long, totalBytes: Long) -> Unit)

interface HttpClient {
    fun get(url: URL, headers: Map<String, String> = HashMap()): HttpResponse?
    fun post(url: URL, headers: Map<String, String>, entity: MultipartEntity): HttpResponse?
    fun post(
        url: URL,
        headers: Map<String, String>,
        entity: MultipartEntity,
        progressCallback: ProgressCallback?
    ): HttpResponse?
}