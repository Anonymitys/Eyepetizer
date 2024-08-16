package com.ekko.repository.model

import kotlinx.serialization.Serializable


@Serializable
data class Tag(
    val id: Int,
    val title: String,
    val link: String
)