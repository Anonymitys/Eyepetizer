package com.ekko.repository.api.interfaces

import com.ekko.repository.model.DailyCard
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/3 15:48
 */
interface HomeApi {

    @GET
    suspend fun getDaily(@Url uri: String): DailyCard
}