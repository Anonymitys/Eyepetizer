package com.ekko.page.paing3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.page.ktx.toItemCard
import com.ekko.page.model.ItemCard
import com.ekko.repository.PageRepository
import com.ekko.repository.model.Card
import kotlinx.serialization.json.jsonPrimitive

class PagingSourceWithInitData(
    private val initData: List<Card>,
    private val repository: PageRepository
) : PagingSource<PageRequest, ItemCard>() {

    override suspend fun load(params: LoadParams<PageRequest>): LoadResult<PageRequest, ItemCard> {
        if (params.key == null) {
            val apiRequest = initData.last().card_data?.body?.api_request
            val nextKey = apiRequest?.let {
                PageRequest(url = it.url, params = it.params?.mapValues { param ->
                    param.value.jsonPrimitive.content
                }?.toMutableMap() ?: mutableMapOf())
            }
            return LoadResult.Page(data = initData.toItemCard(), prevKey = null, nextKey = nextKey)
        }
        val request = params.key?:throw RuntimeException("request empty")
        try {
            val card = repository.getPageMetroData(request.url, request.params)
            val nextKey = card.last_item_id.takeIf { it.isNotEmpty() }?.let { lastItemId ->
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
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override val keyReuseSupported: Boolean
        get() = true

    companion object {
        const val LAST_ITEM_ID = "last_item_id"
    }
}