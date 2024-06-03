package com.ekko.page.paing3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.page.ktx.toItemCard
import com.ekko.page.model.ItemCard
import com.ekko.repository.PageRepository
import com.ekko.repository.model.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonPrimitive

class PagingSourceWithData(
    private val data: List<Card>,
    private val repository: PageRepository
) : PagingSource<PageRequest, ItemCard>() {

    override suspend fun load(params: LoadParams<PageRequest>): LoadResult<PageRequest, ItemCard> {
        if (params.key == null) {
            val apiRequest = data.last().card_data?.body?.api_request
            val nextKey = apiRequest?.let {
                PageRequest(url = it.url, params = it.params?.mapValues { param ->
                    param.value.jsonPrimitive.content
                }?.toMutableMap() ?: mutableMapOf())
            }
            return LoadResult.Page(data = data.toItemCard(), prevKey = null, nextKey = nextKey)
        }
        val request = params.key?:throw RuntimeException("request empty")
        try {
            val card = repository.getPageMetroData(request.url, request.params)
            val nextKey = card.last_item_id.takeIf { it > 0 }?.let { lastItemId ->
                PageRequest(url = request.url,
                    params = request.params.also {
                        it[LAST_ITEM_ID] = lastItemId
                    })
            }
            return LoadResult.Page(data = card.toItemCard(), prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<PageRequest, ItemCard>): PageRequest? {
        return null
    }

    private suspend fun loadFirst(data: List<Card>): List<Card> {
        return withContext(Dispatchers.IO) {
            delay(10)
            data
        }
    }

    override val keyReuseSupported: Boolean
        get() = true

    companion object {
        const val LAST_ITEM_ID = "last_item_id"
    }
}