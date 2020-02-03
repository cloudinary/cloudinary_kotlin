package com.cloudinary.http

import okhttp3.*
import java.io.File
import java.io.InputStream
import java.net.URL
import javax.naming.OperationNotSupportedException


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
            is File -> builder.addFormDataPart(
                "file", part.name,
                RequestBody.create(MediaType.parse("application/octet-stream"), part.value)
            )
            is InputStream -> throw OperationNotSupportedException("OkHttp does not support stream multipart")
        }
    }
}