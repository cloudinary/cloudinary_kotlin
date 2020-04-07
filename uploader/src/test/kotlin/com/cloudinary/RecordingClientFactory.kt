package com.cloudinary

import com.cloudinary.config.ApiConfig
import com.cloudinary.http.*
import java.net.URL


class RecordingClientFactory(userAgent: String, config: ApiConfig) : HttpClientFactory(userAgent, config) {

    private val client = RecordingHttpClient(OkHttpClientFactory(userAgent, config).getClient())

    override fun getClient() = client
}

class RecordingHttpClient(private val realClient: HttpClient) : HttpClient {

    override fun get(url: URL, headers: Map<String, String>): HttpResponse? {
        val response = realClient.get(url, headers)
        log("get", url, headers, null, response)
        return response
    }

    override fun post(url: URL, headers: Map<String, String>, entity: MultipartEntity): HttpResponse? =
        post(url, headers, entity, null)

    override fun post(
        url: URL,
        headers: Map<String, String>,
        entity: MultipartEntity,
        progressCallback: ProgressCallback?
    ): HttpResponse? {
        val response = realClient.post(url, headers, entity, progressCallback)
        log("post", url, headers, entity, response)
        return response
    }
}

private fun log(
    method: String,
    url: URL,
    headers: Map<String, String>,
    entity: MultipartEntity?,
    response: HttpResponse?
) = records.add(LogItem(method, url, headers, entity, response))

val records = mutableListOf<LogItem>()

class LogItem(
    val method: String,
    val url: URL,
    val headers: Map<String, String>,
    val entity: MultipartEntity?,
    val response: HttpResponse?
)