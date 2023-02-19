package com.ekko.repository.model

/**
 * 日报卡片
 * @Author Ekkoe
 * @Date 2023/1/4 11:48
 */
@kotlinx.serialization.Serializable
data class DailyCard(
    val count: Int = 0,
    val itemList: List<Item>? = null,
    val nextPageUrl: String = "",
    val total: Int = 0
)

@kotlinx.serialization.Serializable
data class Item(
    val data: Data,
    val id: Int,
    val type: String
)

@kotlinx.serialization.Serializable
data class Data(
    val actionUrl: String? = null,
    val content: Content? = null,
    val dataType: String,
    val header: Header? = null,
    val id: Int = 0,
    val subTitle: String? = null,
    val text: String? = null,
    val type: String? = null
)

@kotlinx.serialization.Serializable
data class Provider(
    val alias: String,
    val icon: String,
    val name: String
)

@kotlinx.serialization.Serializable
data class Tag(
    val actionUrl: String,
    val bgPicture: String,
    val communityIndex: Int,
    val desc: String?,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val tagRecType: String
)

@kotlinx.serialization.Serializable
data class VideoPosterBean(
    val fileSizeStr: String,
    val scale: Double,
    val url: String
)

@kotlinx.serialization.Serializable
data class WebUrl(
    val forWeibo: String,
    val raw: String
)