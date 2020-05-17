package com.cloudinary.upload

import com.cloudinary.config.ApiConfig
import com.cloudinary.http.*
import com.cloudinary.upload.request.AbstractUploaderRequest
import com.cloudinary.upload.response.Error
import com.cloudinary.upload.response.UploadError
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.apiSignRequest
import com.cloudinary.util.asCloudinaryTimestamp
import com.cloudinary.util.uploadError
import java.net.URL

private const val DEFAULT_PREFIX = "https://api.cloudinary.com"
private const val API_VERSION = "v1_1"

internal typealias StringToResult<T> = (String) -> T?

internal class NetworkDelegate(
    userAgent: String,
    apiConfig: ApiConfig,
    private val clientFactory: HttpClientFactory = instantiateClientFactory(userAgent, apiConfig)
) {
    private val parsableStatusCodes = intArrayOf(200, 400, 401, 403, 404, 420, 500)

    internal fun <T> callApi(
        request: AbstractUploaderRequest<*>,
        action: String,
        adapter: StringToResult<T>
    ): UploaderResponse<T> {
        val (url, params) = prepare(action, request)

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

    private fun prepare(action: String, request: AbstractUploaderRequest<*>): PreparedRequest {
        val paramsMap = request.buildParams()

        val config = request.configuration
        val prefix = config.uploadPrefix ?: DEFAULT_PREFIX
        val cloudName = config.cloudName
        val resourceType = if (action != "delete_by_token") (request.options.resourceType ?: "image") else null

        if (requiresSigning(action, paramsMap, request)) {
            config.apiKey?.let {
                // no signature - we need to sign using api secret if present:
                val apiSecret = request.configuration.apiSecret
                    ?: throw IllegalArgumentException("Must supply api_secret")

                paramsMap["timestamp"] = (System.currentTimeMillis() / 1000L).asCloudinaryTimestamp()
                paramsMap["signature"] = apiSignRequest(paramsMap, apiSecret)

                paramsMap["api_key"] = it
            }
        }

        val url = listOfNotNull(prefix, API_VERSION, cloudName, resourceType, action).joinToString("/")

        return PreparedRequest(url, paramsMap)
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

    private fun requiresSigning(
        action: String,
        params: Map<String, Any>,
        request: AbstractUploaderRequest<*>
    ): Boolean {
        val missingSignature = params["signature"]?.toString().isNullOrBlank()
        val signedRequest = !request.options.unsigned
        val actionRequiresSigning = action != "delete_by_token"
        return missingSignature && signedRequest && actionRequiresSigning
    }

    internal data class PreparedRequest(val url: String, val params: MutableMap<String, Any>)
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