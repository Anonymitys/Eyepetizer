package com.ekko.repository.model

import kotlinx.serialization.Serializable

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 18:41
 */

@Serializable
data class FeedCoverVideo(
    val video_id: String = "",
    val title: String = "",
    val duration: Duration? = null,
    val play_url: String = "",
    val preview_url: String = "",
    val recommend_level: String = "",
    val tags: List<VideoTag>? = null,
    val cover: Cover? = null,
    val author: Author? = null,
    val resource_id: Long = 0,
    val resource_type: String = "",
)

@Serializable
data class Duration(
    val value: Long = 0,
    val text: String = "",
)

@Serializable
data class VideoTag(
    val id: Long = 0,
    val title: String = "",
    val link: String = "",
)