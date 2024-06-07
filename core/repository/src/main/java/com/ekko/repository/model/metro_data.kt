package com.ekko.repository.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 16:00
 */

@Serializable
data class MetroList(
    val api_request: ApiRequest? = null,
    val metro_list: List<MetroCard>? = null,
)

@Serializable
data class MetroCard(
    val metro_id: Long = 0,
    val type: String = "",
    val alias_name: String = "",
    val style: Style? = null,
    val metro_unique_id: String = "",
    val metro_data: JsonObject,
    val link: String = ""
)

