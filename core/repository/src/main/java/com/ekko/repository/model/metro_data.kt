package com.ekko.repository.model

import com.ekko.base.ktx.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 16:00
 */

@Serializable
data class MetroList(
    val api_request: ApiRequest? = null,
    val metro_list: List<MetroCard<JsonObject>>? = null,
)

@Serializable
data class MetroCard<T>(
    val metro_id: Long = 0,
    val type: String = "",
    val alias_name: String = "",
    val style: Style? = null,
    val metro_unique_id: String = "",
    val metro_data: T,
    val link: String = ""
)

inline fun <reified T> MetroCard<JsonObject>.toMetroCard(): MetroCard<T> {
    return MetroCard(
        metro_id = metro_id,
        type = type,
        alias_name = alias_name,
        style = this.style,
        metro_unique_id = metro_unique_id,
        metro_data = json.decodeFromJsonElement(serializer(typeOf<T>()), metro_data) as T,
        link = this.link
    )
}

fun MetroCard<JsonObject>.toMetroCard(kType: KType): MetroCard<Any> {
    return MetroCard(
        metro_id = metro_id,
        type = type,
        alias_name = alias_name,
        style = this.style,
        metro_unique_id = metro_unique_id,
        metro_data = json.decodeFromJsonElement(serializer(kType), metro_data) as Any,
        link = this.link
    )
}

