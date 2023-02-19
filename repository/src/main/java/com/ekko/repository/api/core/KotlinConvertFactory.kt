package com.ekko.repository.api.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/30 17:57
 */
class KotlinConvertFactory : Converter.Factory() {

    @OptIn(ExperimentalSerializationApi::class)
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return KotlinConvertor(serializer(type))
    }
}

class KotlinConvertor<T>(private val serializer: KSerializer<T>) : Converter<ResponseBody, T> {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override fun convert(value: ResponseBody): T {
        return json.decodeFromString(serializer, value.string())
    }
}