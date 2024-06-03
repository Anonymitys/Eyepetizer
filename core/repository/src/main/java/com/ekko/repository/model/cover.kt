package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val url: String = "",
    val img_info: ImageInfo? = null,
)