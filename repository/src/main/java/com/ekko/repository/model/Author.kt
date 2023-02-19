package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:02
 */
@kotlinx.serialization.Serializable
data class Author(
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: Follow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: Shield,
    val videoNum: Int
)