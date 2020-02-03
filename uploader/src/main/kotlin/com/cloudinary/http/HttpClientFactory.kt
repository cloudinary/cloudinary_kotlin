package com.cloudinary.http

import com.cloudinary.Cloudinary
import okhttp3.OkHttpClient
import org.apache.http.impl.client.HttpClientBuilder


abstract class HttpClientFactory(protected val cloudinary: Cloudinary) {
    abstract fun getClient(): HttpClient
}

class ApacheHttpClient45Factory(cloudinary: Cloudinary) : HttpClientFactory(cloudinary) {
    override fun getClient(): HttpClient {
        return ApacheHttpClient45Adapter(HttpClientBuilder.create().setUserAgent("${cloudinary.userAgent} ApacheHTTPComponents/4.5").build())
    }
}

class OkHttpClientFactory(cloudinary: Cloudinary) : HttpClientFactory(cloudinary) {
    override fun getClient(): HttpClient {
        return OkHttpClientAdapter(OkHttpClient.Builder().addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .header("User-Agent", "${cloudinary.userAgent} OkHttp/3.11")
                    .build()
            )
        }.build())
    }
}

class HttpUrlConnectionFactory(cloudinary: Cloudinary) : HttpClientFactory(cloudinary) {
    override fun getClient(): HttpClient {
        return HttpUrlConnectionAdapter("${cloudinary.userAgent} HttpUrlConnection")
    }
}

fun Cloudinary.newHttpClientFactory(): HttpClientFactory {
    // TODO return the correct one based on config (or whatever is present):
    return HttpUrlConnectionFactory(this)
}
