package com.ekko.repository

import com.ekko.api.core.ApiService
import com.ekko.repository.api.DetailApi
import com.ekko.repository.model.RecommendCard
import com.ekko.repository.model.VideoItemCard
import javax.inject.Inject

class PlayDetailRepository @Inject constructor(apiService: ApiService) {
    private val detailApi = apiService.createApi<DetailApi>()


    suspend fun playDetail(resourceId: String, resourceType: String): VideoItemCard =
        detailApi.playDetail(resourceId, resourceType)

    suspend fun relateRecommend(resourceId: String, resourceType: String): RecommendCard =
        detailApi.relateRecommend(resourceId, resourceType)
}