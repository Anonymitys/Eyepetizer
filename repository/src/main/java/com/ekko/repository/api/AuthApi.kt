package com.ekko.repository.api

import com.ekko.repository.model.AuthToken

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 18:11
 */
interface AuthApi {

    suspend fun getAuthToken():AuthToken

}