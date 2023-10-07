package com.ekko.repository.model

import kotlinx.serialization.Serializable


@Serializable
data class WaterfallCoverImage(
    val image_id: Long = 0,
    val title: String = "",
    val cover: Cover? = null,
    val author: Author? = null,
    val liked: Boolean = false,
    val image_count: Long = 0

)