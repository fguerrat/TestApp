package com.lacalera.testapp.data.remote.interceptor

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BaseUrlInterceptor @Inject constructor() : Interceptor {

    @Volatile
    var baseUrl: String? = null

    fun updateBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newBaseUrl = baseUrl
        val newUrl = if (newBaseUrl != null) {
            originalUrl.newBuilder()
                .scheme(newBaseUrl.toHttpUrl().scheme)
                .host(newBaseUrl.toHttpUrl().host)
                .port(newBaseUrl.toHttpUrl().port)
                .build()
        } else {
            originalUrl
        }

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

