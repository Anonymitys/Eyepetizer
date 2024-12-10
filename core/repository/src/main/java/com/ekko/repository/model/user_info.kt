package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val author_id: Int,
    val avatar: Avatar,
    val birthday: Int,
    val brief: String,
    val city: String,
    val collected_count: Int,
    val constellation: String,
    val country: String,
    val cover: String,
    val description: String,
    val enable_share: Boolean,
    val expert: Boolean,
    val fans_count: Int,
    val fans_count_link: String,
    val featured_count: Int,
    val follow_count: Int,
    val follow_count_link: String,
    val follow_page_label: String,
    val followed: Boolean,
    val gender: String,
    val is_mine: Boolean,
    val item_count: Int,
    val job: String,
    val link: String,
    val location: String,
    val medal_count: Int,
    val medal_count_link: String,
    val mobile: String,
    val nav_tabs: NavTabs,
    val nick: String,
    val private_msg_link: String,
    val recommend_count: Int,
    val shared_count: Int,
    val show_follow_btn: Boolean,
    val show_private_msg_btn: Boolean,
    val type: String,
    val uid: Int,
    val university: String,
    val watch_history_link: String
)

@Serializable
data class ImgInfo(
    val height: Int,
    val scale: Int,
    val width: Int
)

@Serializable
data class NavTabs(
    val nav_list: List<NavListItem>,
    val style: String
)

@Serializable
data class NavListItem(
    val api_request: ApiRequest,
    val default_display: Boolean,
    val force_refresh: Boolean,
    val icon_type: String,
    val is_recommend: Boolean,
    val page_label: String,
    val page_type: String,
    val page_url: String,
    val page_url_parameter: String,
    val title: String,
    val url: String
)