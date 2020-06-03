package com.cloudinary.http

import okhttp3.*
import java.io.File
import java.io.InputStream
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
    ): HttpResponse {
        val bodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        entity.parts.forEach { (name, value) -> addPart(bodyBuilder, name, value) }

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
            return HttpResponse(
                response.code(),
                response.body()?.string(),
                responseHeaders
            )
        }
    }

    private fun addPart(builder: MultipartBody.Builder, name: String, value: Any) {
        when (value) {
            is String -> builder.addFormDataPart(name, value)
            is File -> builder.addFormDataPart(
                "file",
                name,
                RequestBody.create(APPLICATION_OCTET_STREAM, value)
            )
            is ByteArray -> builder.addFormDataPart(
                "file", name, RequestBody.create(APPLICATION_OCTET_STREAM, value)
            )
            is InputStream -> throw Error(IllegalArgumentException("OkHttp does not support uploading streams"))
        }
    }
}