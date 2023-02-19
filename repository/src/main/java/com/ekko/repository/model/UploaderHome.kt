package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 15:57
 */

@kotlinx.serialization.Serializable
data class UploaderHome(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<UploaderItem>,
    val nextPageUrl: String? = null,
    val total: Int
)

@kotlinx.serialization.Serializable
data class UploaderItem(
    val adIndex: Int,
    val data: UploaderData,
    val id: Int,
    val type: String
)

@kotlinx.serialization.Serializable
data class UploaderData(
    val actionUrl: String = "",
    val author: Author? = null,
    val category: String = "",
    val collected: Boolean = false,
    val consumption: Consumption? = null,
    val count: Int = 0,
    val cover: Cover? = null,
    val dataType: String = "",
    val date: Long = 0,
    val description: String = "",
    val descriptionEditor: String = "",
    val duration: Int = 0,
    val expert: Boolean = false,
    val follow: Follow? = null,
    val header: Header? = null,
    val icon: String = "",
    val iconType: String = "",
    val id: Int = 0,
    val idx: Int = 0,
    val ifLimitVideo: Boolean = false,
    val ifPgc: Boolean = false,
    val ifShowNotificationIcon: Boolean = false,
    val itemList: List<ItemX>? = null,
    val library: String = "",
    val playInfo: List<PlayInfo>? = null,
    val playUrl: String = "",
    val played: Boolean = false,
    val reallyCollected: Boolean = false,
    val releaseTime: Long = 0,
    val resourceType: String? = null,
    val searchWeight: Int = 0,
    val tags: List<TagXX>? = null,
    val text: String = "",
    val title: String = "",
    val type: String = "",
    val uid: Int = 0,
    val videoPosterBean: VideoPosterBeanXX? = null,
    val webUrl: WebUrlXX? = null
)

@kotlinx.serialization.Serializable
data class ItemX(
    val adIndex: Int = 0,
    val data: DataX? = null,
    val id: Int = 0,
    val type: String = ""
)

@kotlinx.serialization.Serializable
data class TagXX(
    val actionUrl: String = "",
    val bgPicture: String = "",
    val communityIndex: Int = 0,
    val desc: String = "",
    val haveReward: Boolean = false,
    val headerImage: String = "",
    val id: Int = 0,
    val ifNewest: Boolean = false,
    val name: String = "",
    val tagRecType: String = ""
)

@kotlinx.serialization.Serializable
data class VideoPosterBeanXX(
    val fileSizeStr: String = "",
    val scale: Double = 0.0,
    val url: String = ""
)

@kotlinx.serialization.Serializable
data class WebUrlXX(
    val forWeibo: String = "",
    val raw: String = ""
)

@kotlinx.serialization.Serializable
data class DataX(
    val author: AuthorX? = null,
    val category: String = "",
    val collected: Boolean = false,
    val content: UploaderContent? = null,
    val cover: CoverXX? = null,
    val dataType: String = "",
    val date: Long = 0,
    val description: String = "",
    val descriptionEditor: String = "",
    val descriptionPgc: String = "",
    val duration: Int = 0,
    val header: HeaderX? = null,
    val id: Int = 0,
    val idx: Int = 0,
    val ifLimitVideo: Boolean = false,
    val library: String = "",
    val playInfo: List<PlayInfo>? = null,
    val playUrl: String = "",
    val played: Boolean = false,
    val reallyCollected: Boolean = false,
    val releaseTime: Long = 0,
    val remark: String = "",
    val resourceType: String = "",
    val searchWeight: Int = 0,
    val slogan: String = "",
    val subtitles: List<String>? = null,
    val thumbPlayUrl: String = "",
    val title: String = "",
    val titlePgc: String = "",
    val type: String = "",
)

@kotlinx.serialization.Serializable
data class AuthorX(
    val approvedNotReadyVideoCount: Int = 0,
    val description: String = "",
    val expert: Boolean = false,
    val follow: Follow? = null,
    val icon: String = "",
    val id: Int = 0,
    val ifPgc: Boolean = false,
    val latestReleaseTime: Long = 0,
    val link: String = "",
    val name: String = "",
    val recSort: Int = 0,
    val videoNum: Int = 0
)

@kotlinx.serialization.Serializable
data class UploaderContent(
    val adIndex: Int = 0,
    val data: DataXX? = null,
    val id: Int = 0,
    val tag: List<Tag>? = null,
    val type: String = ""
)

@kotlinx.serialization.Serializable
data class CoverXX(
    val blurred: String = "",
    val detail: String = "",
    val feed: String = "",
    val homepage: String = "",
)

@kotlinx.serialization.Serializable
data class HeaderX(
    val actionUrl: String = "",
    val cover: String = "",
    val description: String = "",
    val icon: String = "",
    val iconType: String = "",
    val id: Int = 0,
    val label: String = "",
    val showHateVideo: Boolean = false,
    val time: Long = 0,
    val title: String = ""
)

@kotlinx.serialization.Serializable
data class DataXX(
    val author: AuthorXX? = null,
    val category: String = "",
    val collected: Boolean = false,
    val cover: Cover? = null,
    val dataType: String = "",
    val date: Long = 0,
    val description: String = "",
    val descriptionEditor: String = "",
    val descriptionPgc: String = "",
    val duration: Int = 0,
    val id: Int = 0,
    val idx: Int = 0,
    val ifLimitVideo: Boolean = false,
    val library: String = "",
    val playInfo: List<PlayInfo>? = null,
    val playUrl: String = "",
    val played: Boolean = false,
    val provider: Provider? = null,
    val reallyCollected: Boolean = false,
    val releaseTime: Long = 0,
    val resourceType: String = "",
    val searchWeight: Int = 0,
    val tags: List<Tag>? = null,
    val title: String = "",
    val titlePgc: String = "",
    val type: String = "",
    val videoPosterBean: VideoPosterBean? = null,
    val webUrl: WebUrl? = null
)

@kotlinx.serialization.Serializable
data class AuthorXX(
    val approvedNotReadyVideoCount: Int = 0,
    val description: String = "",
    val expert: Boolean = false,
    val follow: Follow? = null,
    val icon: String = "",
    val id: Int = 0,
    val ifPgc: Boolean = false,
    val latestReleaseTime: Long = 0,
    val link: String = "",
    val name: String = "",
    val recSort: Int = 0,
    val shield: Shield? = null,
    val videoNum: Int = 0
)

