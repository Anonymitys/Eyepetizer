package com.ekko.repository.api

import com.ekko.repository.model.CardList
import com.ekko.repository.model.HotQueries
import com.ekko.repository.model.SearchRecommendKey
import com.ekko.repository.model.SearchResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/recommend/search/get_hot_queries")
    suspend fun getHotQueries(): HotQueries

    @GET("v1/search/search/get_search_recommend_card_list")
    suspend fun getRecommendCardList(): CardList

    @GET("v1/recommend/search/get_pre_search")
    suspend fun getPreSearchKey(@Query("query") query: String): SearchRecommendKey

    @FormUrlEncoded
    @POST("v1/search/search/get_search_result_v2")
    suspend fun getSearchResult(@Field("query") query: String): SearchResult
}