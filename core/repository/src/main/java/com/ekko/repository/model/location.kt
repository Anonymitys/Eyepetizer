package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val area: String,
    val city: String,
    val longitude: Int,
    val latitude: Int
)