package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Long = 0,
    val title: String = "",
    val link: String = ""
)