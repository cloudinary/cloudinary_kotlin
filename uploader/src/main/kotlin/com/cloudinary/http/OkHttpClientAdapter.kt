package com.cloudinary.http

import okhttp3.*
import java.io.File
import java.net.URL

val APPLICATION_OCTET_STREAM = MediaType.parse("application/octet-stream")

class OkHttpClientAdapter(private var client: OkHttpClient) : HttpClient {

    override fun post(url: URL, headers: Map<String, String>, entity: MultipartEntity) =
        post(url, headers, entity, null)

    override fun post(
        url: URL,
        headers: Map<String, String>,
        entity: MultipartEntity,
        progressCallback: ProgressCallback?
    ): HttpResponse? {
        val bodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        entity.parts.forEach { addPart(bodyBuilder, it) }

        val requestBuilder = Request.Builder()
            .url(url.toExternalForm())
            .post(bodyBuilder.build())

        return execute(headers, requestBuilder)
    }

    override fun get(url: URL, headers: Map<String, String>): HttpResponse? {
        val requestBuilder = Request.Builder()
            .url(url.toExternalForm())
            .get()

        return execute(headers, requestBuilder)
    }

    private fun execute(
        headers: Map<String, String>,
        requestBuilder: Request.Builder
    ): HttpResponse {
        headers.forEach { requestBuilder.addHeader(it.key, it.value) }

        this.client.newCall(requestBuilder.build()).execute().use { response ->
            val responseHeaders = response.headers().names().associateBy({ it!! }, { response.headers().get(it)!! })
            return HttpResponse(response.code(), response.body()?.string(), responseHeaders)
        }
    }

    private fun addPart(builder: MultipartBody.Builder, part: MultipartEntity.Part) {
        when (part.value) {
            is String -> builder.addFormDataPart(part.name, part.value)
            is URL -> builder.addFormDataPart(part.name, part.value.toString())
            is File -> builder.addFormDataPart(
                "file",
                part.name,
                RequestBody.create(APPLICATION_OCTET_STREAM, part.value)
            )
            is ByteArray -> builder.addFormDataPart(
                "file", part.name, RequestBody.create(APPLICATION_OCTET_STREAM, part.value)
            )
        }
    }
}