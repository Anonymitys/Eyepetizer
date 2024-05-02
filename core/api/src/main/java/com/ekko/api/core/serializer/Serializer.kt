package com.ekko.api.core.serializer

import com.ekko.api.core.ApiResponse
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import java.lang.RuntimeException
import java.lang.reflect.Type

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 17:07
 */
sealed class Serializer {

    abstract val format: SerialFormat

    abstract fun <T> fromResponseBody(
        type: KSerializer<T>,
        body: ResponseBody
    ): T

    class FromString(override val format: StringFormat) : Serializer() {
        override fun <T> fromResponseBody(
            type: KSerializer<T>,
            body: ResponseBody
        ): T {
            val string = body.string()
            val response = format.decodeFromString(ApiResponse.serializer(type), string)
            if (response.code != 0L || response.result == null) {
                throw RuntimeException("接口错误：${response.code}:${string}")
            }
            return response.result
        }
    }

    class fromBytes(override val format: BinaryFormat) : Serializer() {
        override fun <T> fromResponseBody(
            type: KSerializer<T>,
            body: ResponseBody
        ): T {
            val bytes = body.bytes()
            val response = format.decodeFromByteArray(ApiResponse.serializer(type), bytes)
            if (response.code != 0L || response.result == null) {
                throw RuntimeException("接口错误：${response.code}")
            }
            return response.result
        }
    }
}