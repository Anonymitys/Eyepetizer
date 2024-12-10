package com.ekko.repository.api

import com.ekko.repository.model.UserInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInfoApi {

    @GET("v1/user/center/get_user_info")
    suspend fun userInfo(@Query("uid") uid: Long): UserInfo
}