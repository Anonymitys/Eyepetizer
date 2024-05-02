package com.ekko.repository.model

import kotlinx.serialization.Serializable

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 18:43
 */

@Serializable
data class Author(
    val uid: Long = 0,
    val nick: String = "",
    val description: String = "",
    val avatar: Avatar? = null,
    val link: String = "",
    val type: String = "",
    val followed: Boolean = false,
)

@Serializable
data class Avatar(
    val url: String = "",
    val img_info: ImageInfo? = null,
    val shape: String = "",
)

@Serializable
data class ImageInfo(
    val width: Double = 0.0,
    val height: Double = 0.0,
    val scale: Double = 0.0,
)