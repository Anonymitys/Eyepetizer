package com.ekko.repository.model

import kotlinx.serialization.Serializable


@Serializable
data class RecommendCard(val item_list: List<VideoItemCard>? = null)

@Serializable
data class VideoItemCard(
    val itemId: String = "",
    val publish_time: String = "",
    val raw_publish_time: String = "",
    val location: Location? = null,
    val topics: List<Topic> = listOf(),
    val category: Category? = null,
    val type: String = "",
    val text: String = "",
    val video: Video? = null,
    val recommend_level: String = "",
    val author: Author? = null,
    val consumption: Consumption? = null,
    val liked: Boolean = false,
    val collected: Boolean = false,
    val resource_id: String = "",
    val resource_type: String = "",
    val private_msg_link: String = "",
    val is_mine: Boolean = false,
    val link: String = "",
    val app_link: String = "",
    val real_location: String = "",
)

@Serializable
data class Category(
    val id: Int = 0,
    val name: String = ""
)

@Serializable
data class Video(
    val video_id: String = "",
    val title: String = "",
    val duration: Duration? = null,
    val play_ctrl: PlayCtrl? = null,
    val play_url: String = "",
    val play_info: List<PlayInfo> = emptyList(),
    val recommend_level: String = "",
    val tags: List<Tag> = emptyList(),
    val cover: Cover? = null,
    val width: Int = 0,
    val height: Int = 0,
    val poster: Poster? = null
)

@Serializable
data class PlayCtrl(
    val autoplay: Boolean = false,
    val autoplay_times: Int = 0,
    val need_wifi: Boolean = false,
    val need_cellular: Boolean = false
)

@Serializable
data class PlayInfo(
    val height: Long = 0,
    val width: Long = 0,
    val name: String,
    val type: String,
    val url: String,
    val url_list: List<UrlList>
)

@Serializable
data class UrlList(
    val name: String,
    val url: String,
    val size: Int
)

@Serializable
data class Poster(
    val cover_img: Cover? = null,
    val url: String,
    val scale: Double,
    val duration: Int,
    val size: String
)