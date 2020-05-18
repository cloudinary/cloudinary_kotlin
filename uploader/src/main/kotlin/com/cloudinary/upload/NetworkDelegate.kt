package com.cloudinary.upload

import com.cloudinary.config.ApiConfig
import com.cloudinary.http.*
import com.cloudinary.upload.request.AbstractUploaderRequest
import com.cloudinary.upload.response.Error
import com.cloudinary.upload.response.UploadError
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.uploadError
import java.net.URL

internal typealias StringToResult<T> = (String) -> T?

internal class NetworkDelegate(
    userAgent: String,
    apiConfig: ApiConfig,
    private val clientFactory: HttpClientFactory = instantiateClientFactory(userAgent, apiConfig)
) {
    private val parsableStatusCodes = intArrayOf(200, 400, 401, 403, 404, 420, 500)

    internal fun <T> callApi(
        request: AbstractUploaderRequest<*>,
        url: String,
        params: MutableMap<String, Any>,
        adapter: StringToResult<T>
    ): UploaderResponse<T> {

        // TODO error handling
        val post = clientFactory.getClient().post(
            URL(url), request.options.headers,
            MultipartEntityImpl().also { entity ->
                request.payload?.let { entity.addPayloadPart(it, request.options.filename) }
                params.forEach { param ->
                    if (param.value is Collection<*>) {
                        (param.value as Collection<*>).forEach {
                            entity.addTextPart("${param.key}[]", it.toString())
                        }
                    } else {
                        entity.addTextPart(param.key, param.value.toString())
                    }
                }
            },
            request.progressCallback
        )
            ?: throw Exception()

        // TODO error handling
        return processResponse(post, adapter)
    }

    private fun <T> processResponse(httpResponse: HttpResponse, adapter: StringToResult<T>): UploaderResponse<T> {
        val result: UploaderResponse<T>
        if (parsableStatusCodes.contains(httpResponse.httpStatusCode)) {
            result = if (httpResponse.httpStatusCode == 200) {
                // parse result body
                // TODO error handling
                httpResponse.content?.let { UploaderResponse(adapter(it), null) } ?: throw Exception()
            } else {
                // parse error body
                // TODO error handling
                val error: UploadError = httpResponse.uploadError()
                    ?: httpResponse.headers["X-Cld-Error"]?.let { UploadError(Error(it)) }
                    ?: throw Exception("Unknown error")

                UploaderResponse(null, error)
            }
        } else {
            // TODO error handling
            throw Exception("Unknown error")
        }

        return result
    }
}

private fun instantiateClientFactory(
    userAgent: String,
    apiConfig: ApiConfig
) = run {
    when {
        exists("org.apache.http.client.HttpClient") -> ApacheHttpClient45Factory(
            userAgent,
            apiConfig
        )
        exists("okhttp3.OkHttpClient") -> OkHttpClientFactory(userAgent, apiConfig)
        else -> HttpUrlConnectionFactory(userAgent, apiConfig)
    }
}

private fun exists(factoryCanonicalName: String): Boolean {
    return try {
        Class.forName(factoryCanonicalName)
        true
    } catch (ignored: Exception) {
        false
    }
}