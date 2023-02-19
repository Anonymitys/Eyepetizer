package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:00
 */
@kotlinx.serialization.Serializable
data class PlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<Url>,
    val width: Int
)