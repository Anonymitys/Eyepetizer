package com.ekko.repository.api.interfaces

import com.ekko.repository.model.DailyCard
import com.ekko.repository.model.UploaderHome
import com.ekko.repository.model.UploaderInfo
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/3 13:21
 */
interface UploaderApi {
    @GET("api/v5/userInfo/tab") suspend fun getUploaderInfo(
        @Query("id") id: String,
        @Query("userType") type: String
    ): UploaderInfo

    /**
     * UP主作品
     */
    @GET suspend fun getUploaderWorks(@Url uri: String): DailyCard

    /**
     * UP主首页
     */
    @GET suspend fun getUploaderHome(@Url uri: String): UploaderHome
}