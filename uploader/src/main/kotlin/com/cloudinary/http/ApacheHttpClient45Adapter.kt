package com.cloudinary.http

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import java.io.File
import java.io.InputStream
import java.net.URL

class ApacheHttpClient45Adapter(private val client: org.apache.http.client.HttpClient) : HttpClient {
    override fun get(url: URL, headers: Map<String, String>): HttpResponse? {
        val httpGet = HttpGet(url.toExternalForm())
        headers.keys.forEach { httpGet.addHeader(it, headers[it]) }
        return execute(httpGet)
    }

    override fun post(url: URL, headers: Map<String, String>, entity: MultipartEntity): HttpResponse? =
        post(url, headers, entity, null)

    override fun post(
        url: URL,
        headers: Map<String, String>,
        entity: MultipartEntity,
        progressCallback: ProgressCallback?
    ): HttpResponse? {
        val httpPost = HttpPost(url.toExternalForm())
        val builder = MultipartEntityBuilder.create()
        entity.parts.forEach { addPart(builder, it) }
        httpPost.entity = builder.build()
        return execute(httpPost)
    }

    private fun execute(httpRequest: HttpRequestBase): HttpResponse {
        val response = client.execute(httpRequest)
        val responseHeaders = response.allHeaders.associateBy({ it.name }, { it.value })
        val stringResponse = response.entity.content.bufferedReader().use { it.readText() }
        return HttpResponse(response.statusLine.statusCode, stringResponse, responseHeaders)
    }

    private fun addPart(builder: MultipartEntityBuilder, part: MultipartEntity.Part) {
        when (part.value) {
            is String -> builder.addTextBody(part.name, part.value)
            is File -> builder.addBinaryBody("file", part.value)
            is InputStream -> builder.addBinaryBody("file", part.value, ContentType.APPLICATION_OCTET_STREAM, part.name)
        }
    }
}