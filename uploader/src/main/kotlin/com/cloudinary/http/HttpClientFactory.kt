package com.cloudinary.http

import com.cloudinary.Cloudinary
import com.cloudinary.config.ApiConfig
import okhttp3.OkHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import java.util.concurrent.TimeUnit


abstract class HttpClientFactory(protected val userAgent: String, protected val config: ApiConfig) {
    abstract fun getClient(): HttpClient
}

class ApacheHttpClient45Factory(userAgent: String, apiConfig: ApiConfig) : HttpClientFactory(userAgent, apiConfig) {
    override fun getClient(): HttpClient {
        return ApacheHttpClient45Adapter(HttpClientBuilder.create().setUserAgent("$userAgent ApacheHTTPComponents/4.5").build())
    }
}

class OkHttpClientFactory(userAgent: String, apiConfig: ApiConfig) : HttpClientFactory(userAgent, apiConfig) {
    override fun getClient(): HttpClient {
        return OkHttpClientAdapter(OkHttpClient.Builder()
            .readTimeout(config.readTimeout.toLong(), TimeUnit.SECONDS)
            .connectTimeout(config.connectTimeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .header("User-Agent", "$userAgent OkHttp/3.11")
                        .build()
                )
            }.build()
        )
    }
}

class HttpUrlConnectionFactory(userAgent: String, apiConfig: ApiConfig) : HttpClientFactory(userAgent, apiConfig) {
    override fun getClient(): HttpClient {
        return HttpUrlConnectionAdapter("$userAgent HttpUrlConnection", config)
    }
}

fun Cloudinary.newHttpClientFactory(): HttpClientFactory {
    // TODO return the correct one based on config (or whatever is present):
    return HttpUrlConnectionFactory(userAgent, config.apiConfig)
}
