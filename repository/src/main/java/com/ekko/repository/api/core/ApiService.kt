package com.ekko.repository.api.core

import com.ekko.repository.EyepetizerContext
import com.ekko.repository.api.interfaces.HomeApi
import com.ekko.repository.api.interfaces.UploaderApi
import com.google.android.gms.net.CronetProviderInstaller
import com.google.net.cronet.okhttptransport.CronetInterceptor
import okhttp3.OkHttpClient
import org.chromium.net.CronetEngine
import retrofit2.Retrofit

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/3 15:34
 */
val apiService by lazy {
    ApiService()
}

class ApiService {

    private var retrofit: Retrofit

    init {
        CronetProviderInstaller.installProvider(EyepetizerContext.application())
        // val callFactory = CronetCallFactory.newBuilder(
        //     CronetEngine.Builder(EyepetizerContext.application()).build()
        // ).build()

        val callFactory = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(
                CronetInterceptor.newBuilder(
                    CronetEngine.Builder(EyepetizerContext.application()).build()
                ).build()
            )
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(KotlinConvertFactory())
            .build()
    }

    val homeApi: HomeApi = retrofit.create(HomeApi::class.java)

    val uploaderApi: UploaderApi = retrofit.create(UploaderApi::class.java)

    companion object {
        const val BASE_URL = "http://baobab.kaiyanapp.com"
    }
}