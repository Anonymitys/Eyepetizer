package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class CardHeader(
    val text: String = "",
    val link: String = ""
)