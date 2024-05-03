package com.ekko.api.core.sign

import kotlinx.serialization.Serializable

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 18:09
 */

@Serializable
data class AuthToken(
    val access_token: String,
    val device_id: String,
    val expires_in: Int,
    val refresh_token: String,
    val server_timestamp: Int
)