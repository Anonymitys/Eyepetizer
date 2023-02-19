package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 15:59
 */
@kotlinx.serialization.Serializable
data class Url(
    val name: String,
    val size: Int,
    val url: String
)