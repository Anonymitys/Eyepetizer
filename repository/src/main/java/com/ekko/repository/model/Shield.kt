package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/3 13:16
 */
@kotlinx.serialization.Serializable
data class Shield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)