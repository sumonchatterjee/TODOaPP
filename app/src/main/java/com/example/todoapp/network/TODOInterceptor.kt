package com.example.todoapp.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TODOInterceptor():Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}