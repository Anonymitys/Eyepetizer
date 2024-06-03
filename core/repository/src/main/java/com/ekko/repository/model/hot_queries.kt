package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class HotQueries(
    val item_list: List<String>? = null
)