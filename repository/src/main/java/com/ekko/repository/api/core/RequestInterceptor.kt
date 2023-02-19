package com.ekko.repository.api.core

import android.os.Build
import android.os.Build.VERSION
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 请求拦截器，加一些公参
 * @Author Ekkoe
 * @Date 2023/2/2 11:19
 */
class RequestInterceptor : Interceptor {
    private val commonParams by lazy {
        mutableMapOf(
           // "udid" to "9d768ace7218c491",
            "deviceModel" to Build.MODEL,
            "system_version_code" to VERSION.SDK_INT.toString(),
            "vc" to "6030012",
            "vn" to "6.3.1"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        when (request.method()) {
            "GET" -> addCommonParamToUrl(request, requestBuilder)
            "POST" -> addCommonParamToBody(request, requestBuilder)
            else -> {
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun addCommonParamToUrl(
        request: Request,
        requestBuilder: Request.Builder
    ) {
        val builder = request.url().newBuilder()
        commonParams.forEach { (key, value) ->
            builder.addQueryParameter(key, value)
        }
        requestBuilder.url(builder.build())
    }

    private fun addCommonParamToBody(
        request: Request,
        requestBuilder: Request.Builder
    ) {
        val requestBodyBuilder = FormBody.Builder()
        (request.body() as? FormBody)?.let { form ->
            repeat(form.size()) {
                requestBodyBuilder.add(form.name(it), form.value(it))
            }
            commonParams.forEach { (key, value) ->
                requestBodyBuilder.add(key, value)
            }
        }
        requestBuilder.post(requestBodyBuilder.build())
    }
}