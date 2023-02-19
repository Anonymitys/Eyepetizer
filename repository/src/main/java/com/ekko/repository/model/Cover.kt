package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:04
 */
@kotlinx.serialization.Serializable
data class Cover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String?
)