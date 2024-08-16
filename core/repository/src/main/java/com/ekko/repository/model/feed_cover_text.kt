package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedCoverText(
    val text_id: String = "",
    val title: String = "",
    val description: String = "",
    val topics: List<Topic>? = null,
    val consumption: Consumption? = null,
    val cover: Cover? = null,
    val text: String = "",
    val resource_type: String = "",
    val resource_id: String = ""

)