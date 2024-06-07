package com.ekko.repository.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiRequest(
    val url: String = "",
    val params: JsonObject? = null,
)