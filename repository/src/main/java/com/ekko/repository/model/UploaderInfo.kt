package com.ekko.repository.model

/**
 * UP主信息
 * @Author Ekkoe
 * @Date 2023/2/3 13:12
 */
@kotlinx.serialization.Serializable
data class UploaderInfo(
    val pgcInfo: UpPgcInfo,
    val tabInfo: UpTabInfo
)

@kotlinx.serialization.Serializable
data class UpPgcInfo(
    val actionUrl: String,
    val area: String,
    val brief: String,
    val collectCount: Int,
    val cover: String?,
    val dataType: String,
    val description: String,
    val expert: Boolean,
    val follow: Follow,
    val followCount: Int,
    val followCountActionUrl: String,
    val gender: String,
    val icon: String,
    val id: Int,
    val medalsActionUrl: String,
    val medalsNum: Int,
    val myFollowCount: Int,
    val myFollowCountActionUrl: String,
    val name: String,
    val registDate: Int,
    val self: Boolean,
    val shareCount: Int,
    val shield: Shield,
    val videoCount: Int,
    val videoCountActionUrl: String,
    val worksRecCount: Int,
    val worksSelectedCount: Int
)

@kotlinx.serialization.Serializable
data class UpTabInfo(
    val defaultIdx: Int,
    val tabList: List<UpTab>
)

@kotlinx.serialization.Serializable
data class UpTab(
    val apiUrl: String,
    val id: Int,
    val name: String,
    val nameType: Int,
    val tabType: Int
)