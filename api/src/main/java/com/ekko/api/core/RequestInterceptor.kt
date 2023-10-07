package com.ekko.api.core

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
          //  "udid" to "4054e3beaad24d56b8f474ed1fbb21f7c3d6c7f8",
            "x-api-key" to "0530ee4341324ce2b26c23fcece80ea2",
            "deviceModel" to Build.MODEL,
            "system_version_code" to VERSION.SDK_INT.toString(),
            "vc" to "7060900",
            "vn" to "7.6.900"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        addHeader(requestBuilder)
        when (request.method()) {
            "GET" -> addCommonParamToUrl(request, requestBuilder)
            "POST" -> addCommonParamToBody(request, requestBuilder)
            else -> {
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun addHeader(requestBuilder: Request.Builder) {
        requestBuilder.header(
            "User-Agent",
            "EYEPETIZER/7060900 (16 X;android;8.1.0;zh_CN;android;7.6.900;cn-bj;yingyongbao;3aa53faf2838e22251dccfc906d8b7b3;WIFI;1080*2070) native/1.0"
        )
        requestBuilder.header(
            "X-THEFAIR-UA",
            "EYEPETIZER/7060900 (16 X;android;8.1.0;zh_CN;android;7.6.900;cn-bj;yingyongbao;3aa53faf2838e22251dccfc906d8b7b3;WIFI;1080*2070) native/1.0"
        )
        requestBuilder.header("X-THEFAIR_CID", "3aa53faf2838e22251dccfc906d8b7b3")
        requestBuilder.header(
            "X-THEFAIR-AUTH",
            "gaxzhbHdEYXPcS2vygoCN8izfa1WNbmtwsb6raMZLUKSsje5gm8Eey1dCNfq/BDRMDG9KgLyB1wIKH8ZXRykNZx4QrG+m+Y15fKjiLPyX49pf9K8RPnDEhKOfcm7E9poHwP6MYNWTGVjn0F6x2BVRNRRz8z0t7+ShsVOYg77HRckxnyItnGNkfyCWcbb78z/zYuTucv78w9tuH8CWHjGgyf8AYo7Po/HFkjlKoH3yMwxdy4Ul+Es9yaAV6q3eeZF3jpQVcl5Cydhh3hPh6loRQ=="
        )
        requestBuilder.header("X-THEFAIR-APPID", "ahpagrcrf2p7m6rg")
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