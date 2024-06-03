package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageInfo(
    val width: Double = 0.0,
    val height: Double = 0.0,
    val scale: Double = 0.0,
)