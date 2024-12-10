package com.ekko.repository

import com.ekko.api.core.ApiService
import com.ekko.repository.api.UserInfoApi
import com.ekko.repository.model.UserInfo
import javax.inject.Inject

class UserInfoRepository @Inject constructor(apiService: ApiService) {
    private val userInfoApi by lazy { apiService.createApi<UserInfoApi>() }

    suspend fun userInfo(uid: Long): UserInfo {
        return userInfoApi.userInfo(uid)
    }
}