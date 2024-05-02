package com.ekko.repository.api

import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 16:38
 */
interface PageApi {

    @FormUrlEncoded
    @POST
    suspend fun getPageCardData(
        @Url url: String,
        @FieldMap params: MutableMap<String, Any>,
    ): CardList

    @FormUrlEncoded
    @POST
    suspend fun getPageMetroData(
        @Url url: String,
        @FieldMap params: MutableMap<String, Any>,
    ): Metro
}