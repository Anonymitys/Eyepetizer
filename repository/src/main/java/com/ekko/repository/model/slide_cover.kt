package com.ekko.repository.model

import kotlinx.serialization.Serializable
import okhttp3.Route


@Serializable
data class SlideCover(
    val image_id: Long = 0,
    val cover: Cover? = null,
    val footer: RouterStyle? = null
)

@Serializable
data class RouterStyle(
    val left: Router? = null,
    val right: Router? = null
)

@Serializable
data class Router(
    val text: String = "",
    val link: String = ""
)