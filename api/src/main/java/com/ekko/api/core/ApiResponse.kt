package com.ekko.api.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 16:12
 */
@Serializable
data class ApiResponse<T>(
    val code: Long,
    val message: JsonObject,
    val result: T? = null,
)