package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/3 13:15
 */

@kotlinx.serialization.Serializable
data class Follow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)