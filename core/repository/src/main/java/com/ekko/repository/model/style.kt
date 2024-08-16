package com.ekko.repository.model

import kotlinx.serialization.Serializable

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 15:50
 */

@Serializable
data class Style(
    val tpl_label: String = "",
    val padding: Params? = null,
)

@Serializable
data class Params(
    val top: Double = 0.0,
    val right: Double = 0.0,
    val bottom: Double = 0.0,
    val left: Double = 0.0,
)
