package com.ekko.api.core.serializer

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 16:00
 */
class KotlinConvertFactory constructor(private val serializer: Serializer) : Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return KotlinConvertor(serializer, serializer(type))
    }
}

class KotlinConvertor<T> internal constructor(
    private val serializer: Serializer,
    private val type: KSerializer<T>
) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {
        return serializer.fromResponseBody(type, value)
    }
}

fun StringFormat.asConverterFactory(): Factory {
    return KotlinConvertFactory(Serializer.FromString(this))
}

fun BinaryFormat.asConverterFactory(): Factory {
    return KotlinConvertFactory(Serializer.fromBytes(this))
}

