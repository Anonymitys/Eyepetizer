package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    val url: String = "",
    val img_info: ImageInfo? = null,
    val shape: String = "",
)