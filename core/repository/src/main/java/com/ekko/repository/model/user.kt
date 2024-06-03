package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Uploader(
    val avatar: Avatar? = null,
    val description: String = "",
    val fans_count: Int = 0,
    val follow_count: Int = 0,
    val followed: Boolean = false,
    val link: String = "",
    val nick: String = "",
    val type: String = "",
    val uid: Int = 0
)