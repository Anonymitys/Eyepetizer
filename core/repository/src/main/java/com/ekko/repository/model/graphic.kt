package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class GraphicCard(
    val image_id: Long = 0,
    val publish_time: String = "",
    val raw_publish_time: String = "",
    val text: String = "",
    val cover: Cover? = null,
    val title: String = "",
    val author: Author? = null,
    val liked: Boolean = false,
    val collected: Boolean = false,
    val is_mine: Boolean = false,
    val show_follow_btn: Boolean = false,
    val consumption: Consumption? = null,
    val topics: List<Topic>? = null

)