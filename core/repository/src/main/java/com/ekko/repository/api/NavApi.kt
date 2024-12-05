package com.ekko.repository.api

import com.ekko.repository.model.NavResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NavApi {

    @POST("v1/card/page/get_nav")
    @FormUrlEncoded
    suspend fun nav(@Field("tab_label") tabLabel: String): NavResponse
}