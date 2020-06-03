package com.cloudinary.upload

import com.cloudinary.http.HttpClientFactory
import com.cloudinary.http.HttpResponse
import com.cloudinary.http.MultipartEntityImpl
import com.cloudinary.http.ProgressCallback
import com.cloudinary.upload.request.Payload
import com.cloudinary.upload.response.Error
import com.cloudinary.upload.response.UploadError
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.extractUploadError
import java.net.URL

internal typealias StringToResult<T> = (String) -> T?

private val parsableStatusCodes = intArrayOf(200, 400, 401, 403, 404, 420, 500)
private val unknownErrorResponse = UploadError(Error("Unknown error"))

class NetworkDelegate(private val clientFactory: HttpClientFactory) {

    fun <T> callApi(request: NetworkRequest<T>): UploaderResponse<T> {

        with(request) {
            val post = clientFactory.getClient().post(
                URL(url), headers,
                MultipartEntityImpl().also { entity ->
                    payload?.let { entity.addPayloadPart(it, filename) }
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
                progressCallback
            )

            return processResponse(post, adapter)
        }
    }

    companion object {
        fun <T> processResponse(
            statusCode: Int,
            content: String?,
            error: String?,
            adapter: StringToResult<T>
        ): UploaderResponse<T> {
            return if (parsableStatusCodes.contains(statusCode)) {
                if (statusCode == 200) {
                    content?.let { UploaderResponse<T>(statusCode, adapter(it), null, it) } ?: UploaderResponse<T>(
                        statusCode,
                        null,
                        unknownErrorResponse,
                        content
                    )

                } else {
                    val uploaderError: UploadError = content?.let { extractUploadError(it) }
                        ?: error?.let { UploadError(Error(it)) }
                        ?: unknownErrorResponse

                    UploaderResponse<T>(statusCode, null, uploaderError, content)
                }
            } else {
                UploaderResponse<T>(
                    statusCode,
                    null,
                    unknownErrorResponse,
                    content
                )
            }
        }

        fun <T> processResponse(httpResponse: HttpResponse, adapter: StringToResult<T>) =
            processResponse(
                httpResponse.httpStatusCode,
                httpResponse.content,
                httpResponse.headers["X-Cld-Error"],
                adapter
            )

    }
}

class NetworkRequest<T>(
    val url: String,
    val filename: String?,
    val headers: Map<String, String>,
    val params: MutableMap<String, Any>,
    val adapter: StringToResult<T>,
    val payload: Payload<*>?,
    val progressCallback: ProgressCallback?
)