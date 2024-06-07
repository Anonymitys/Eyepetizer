package com.ekko.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ekko.page.model.ItemCard
import com.ekko.page.paing3.PageRequest
import com.ekko.page.paing3.PagingSource
import com.ekko.page.paing3.PagingSourceWithInitData
import com.ekko.repository.PageRepository
import com.ekko.repository.SearchRepository
import com.ekko.repository.model.Card
import com.ekko.repository.model.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val pageRepository: PageRepository
) :
    ViewModel() {

    private val cache = mutableMapOf<String, List<Card>>()

    suspend fun getRecommendList(): List<Card> {
        return searchRepository.getRecommendList().list ?: listOf()
    }

    suspend fun getHotQueries(): List<String> {
        return searchRepository.getHotQueries()
    }

    suspend fun getRecommendSearchKey(query: String): List<String> {
        return searchRepository.getRecommendSearchKey(
            query
        ).item_list ?: emptyList()
    }

    suspend fun getSearchResult(query: String): SearchResult {
        return searchRepository.getSearchResult(query).also { result ->
            cache.clear()
            result.item_list.forEach {
                cache[it.nav.title] = it.card_list
            }
        }
    }

    fun getSearchItemResult(type: String) = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        PagingSourceWithInitData(
            cache[type] ?: emptyList(),
            pageRepository,
        )
    }.flow.cachedIn(viewModelScope)
}