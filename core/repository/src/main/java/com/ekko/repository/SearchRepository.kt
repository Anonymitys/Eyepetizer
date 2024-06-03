package com.ekko.repository

import com.ekko.api.core.ApiService
import com.ekko.repository.api.SearchApi
import com.ekko.repository.model.CardList
import com.ekko.repository.model.SearchRecommendKey
import com.ekko.repository.model.SearchResult
import javax.inject.Inject

class SearchRepository @Inject constructor(apiService: ApiService) {

    private val searchApi = apiService.createApi<SearchApi>()

    suspend fun getHotQueries(): List<String> {
        return searchApi.getHotQueries().item_list ?: emptyList()
    }

    suspend fun getRecommendList(): CardList = searchApi.getRecommendCardList()

    suspend fun getRecommendSearchKey(query: String): SearchRecommendKey =
        searchApi.getPreSearchKey(query)

    suspend fun getSearchResult(query: String): SearchResult =
        searchApi.getSearchResult(query)
}