package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Duration(
    val value: Long = 0,
    val text: String = "",
)