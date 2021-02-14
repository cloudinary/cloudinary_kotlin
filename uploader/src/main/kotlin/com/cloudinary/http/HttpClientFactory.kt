package com.cloudinary.http

import com.cloudinary.config.ApiConfig
import okhttp3.OkHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import java.util.concurrent.TimeUnit

interface HttpClientFactory {
    val userAgent: String
    val config: ApiConfig

    fun getClient(): HttpClient
}

class ApacheHttpClient45Factory(
    override val userAgent: String,
    override val config: ApiConfig
) : HttpClientFactory {
    override fun getClient(): HttpClient {
        return ApacheHttpClient45Adapter(
            HttpClientBuilder.create().setUserAgent("$userAgent ApacheHTTPComponents/4.5").build()
        )
    }
}

class OkHttpClientFactory(
    override val userAgent: String,
    override val config: ApiConfig
) : HttpClientFactory {
    override fun getClient(): HttpClient {
        return OkHttpClientAdapter(
            OkHttpClient.Builder()
                .readTimeout(config.readTimeout.toLong(), TimeUnit.SECONDS)
                .connectTimeout(config.connectionTimeout.toLong(), TimeUnit.SECONDS)
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder()
                            .header("User-Agent", "$userAgent OkHttp3")
                            .build()
                    )
                }.build()
        )
    }
}

class HttpUrlConnectionFactory(
    override val userAgent: String,
    override val config: ApiConfig

) : HttpClientFactory {
    override fun getClient(): HttpClient {
        return HttpUrlConnectionAdapter("$userAgent HttpUrlConnection", config)
    }
}
