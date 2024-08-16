package com.ekko.repository.api

import com.ekko.repository.model.RecommendCard
import com.ekko.repository.model.VideoItemCard
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DetailApi {


    @GET("v1/content/item/get_item_detail_v2")
    suspend fun playDetail(
        @Query("resource_id") resourceId: String,
        @Query("resource_type") resourceType: String
    ): VideoItemCard


    @FormUrlEncoded
    @POST("v1/content/item/get_related_recommend")
    suspend fun relateRecommend(
        @Field("resource_id") resourceId: String,
        @Field("resource_type") resourceType: String
    ): RecommendCard
}