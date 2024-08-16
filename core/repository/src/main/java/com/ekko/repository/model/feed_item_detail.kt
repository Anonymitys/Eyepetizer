package com.ekko.repository.model

import kotlinx.serialization.Serializable


@Serializable
data class FeedItemDetailCard(
    val item_id: Long = 0,
    val publish_time: String = "",
    val raw_publish_time: String = "",
    val text: String = "",
    val video: FeedCoverVideo? = null,
    val author: Author? = null,
    val liked: Boolean = false,
    val collected: Boolean = false,
    val is_mine: Boolean = false,
    val show_follow_btn: Boolean = false,
    val consumption: Consumption? = null,
    val topics: List<Topic>? = null

)


@Serializable
data class Consumption(
    val like_count: Long = 0,
    val collection_count: Long = 0,
    val comment_count: Long = 0,
    val share_count: Long = 0
)