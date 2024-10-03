package com.ekko.repository.model

import kotlinx.serialization.Serializable


@Serializable
data class FeedCoverImage(
    val image_id: Long = 0,
    val title: String = "",
    val cover: Cover? = null,
    val resource_type: String = "",
    val resource_id: Long = 0

)