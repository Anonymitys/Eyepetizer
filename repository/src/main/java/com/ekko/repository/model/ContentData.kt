package com.ekko.repository.model

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 16:12
 */
@kotlinx.serialization.Serializable
data class ContentData(
    val author: Author,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String?,
    val descriptionPgc: String?,
    val duration: Int,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val library: String,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val provider: Provider,
    val reallyCollected: Boolean,
    val releaseTime: Long,
    val remark: String?,
    val resourceType: String?,
    val searchWeight: Int,
    val slogan: String?,
    val tags: List<Tag>,
    val thumbPlayUrl: String?,
    val title: String?,
    val titlePgc: String?,
    val type: String?,
    val videoPosterBean: VideoPosterBean? = null,
    val webUrl: WebUrl
)