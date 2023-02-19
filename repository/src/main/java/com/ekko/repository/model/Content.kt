package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:11
 */
@kotlinx.serialization.Serializable
data class Content(
    val data: ContentData,
    val id: Int,
    val type: String
)