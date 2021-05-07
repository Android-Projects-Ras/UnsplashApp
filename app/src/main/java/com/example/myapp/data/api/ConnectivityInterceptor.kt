package com.example.myapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("client_id", UnsplashApi.API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}