package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:07
 */
@kotlinx.serialization.Serializable
data class Header(
    val actionUrl: String,
    val cover: String? = null,
    val description: String? = null,
    val icon: String? = null,
    val iconType: String? = null,
    val id: Int,
    val subTitle: String? = null,
    val time: Long = 0,
    val title: String? = null,
    val issuerName: String? = null
)