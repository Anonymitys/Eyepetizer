package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedCoverTopic(
    val topic_id: String = "",
    val title: String = "",
    val description: String = "",
    val tags: List<VideoTag>? = null,
    val cover: Cover? = null,
    val resource_type: String = "",
    val resource_id: String = ""

)