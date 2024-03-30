package com.ekko.api.core

import android.content.Context
import androidx.annotation.RestrictTo
import com.ekko.api.core.header.HeaderStorage
import com.ekko.api.core.serializer.KotlinConvertFactory
import com.ekko.api.core.serializer.asConverterFactory
import com.ekko.api.core.sign.SignInterceptor
import com.google.android.gms.net.CronetProviderInstaller
import com.google.net.cronet.okhttptransport.CronetInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.chromium.net.CronetEngine
import retrofit2.Retrofit
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 15:55
 */
@Singleton
class ApiService @Inject constructor(
    @ApplicationContext context: Context,
    headerStorage: HeaderStorage
) {

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    val retrofit: Retrofit
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    init {
        CronetProviderInstaller.installProvider(context)
        // val callFactory = CronetCallFactory.newBuilder(
        //     CronetEngine.Builder(EyepetizerContext.application()).build()
        // ).build()
        val callFactory = OkHttpClient.Builder()
            .addInterceptor(SignInterceptor(context, headerStorage))
            .addInterceptor(RequestInterceptor(context, headerStorage))
            .addInterceptor(
                CronetInterceptor.newBuilder(
                    CronetEngine.Builder(context).build()
                ).build()
            )
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(json.asConverterFactory())
            .build()
    }

    inline fun <reified T> createApi(): T {
        return retrofit.create(T::class.java)
    }

    companion object {
        const val BASE_URL = "http://api.eyepetizer.net"
    }
}