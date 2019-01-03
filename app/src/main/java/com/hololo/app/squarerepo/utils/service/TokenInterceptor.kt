package com.hololo.app.squarerepo.utils.service

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var modifiedRequest = request
        modifiedRequest = request.newBuilder()
                .header("Authorization", "token " + refreshToken())
                .build()
        val response = chain.proceed(modifiedRequest)
        val unauthorized = response.code() == 401 || response.code() == 500
        if (unauthorized) {
            modifiedRequest = request.newBuilder()
                    .header("Authorization", "token " + refreshToken())
                    .build()
            return chain.proceed(modifiedRequest)
        }
        return response
    }

    private fun refreshToken(): String {
        return "7a519383a519ddeb8002b496a67ed9cfd6470144"
    }
}