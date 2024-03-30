package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Icon(
    val name: String = "",
    val icon: String = "",
    val link: String = ""
)

@Serializable
data class Icons(
    val icons: List<Icon>
)



