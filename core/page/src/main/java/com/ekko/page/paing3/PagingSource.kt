package com.ekko.page.paing3

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.page.ktx.toItemCard
import com.ekko.page.model.ItemCard
import com.ekko.repository.PageRepository
import kotlinx.serialization.json.jsonPrimitive

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/27 15:28
 */
class PagingSource(
    private val repository: PageRepository,
    private val pageRequest: PageRequest,
) : PagingSource<PageRequest, ItemCard>() {

    override suspend fun load(params: LoadParams<PageRequest>): LoadResult<PageRequest, ItemCard> {
        val request = params.key ?: pageRequest
        return try {
            val pair = if (request.url.contains(CALL_METRO_LIST)) {
                val metro = repository.getPageMetroData(request.url, request.params)
                val requestParams = metro.last_item_id.takeIf { it.isNotEmpty() }?.let {
                    PageRequest(request.url, HashMap<String, Any>().apply {
                        putAll(request.params)
                        put(LAST_ITEM_ID, it)
                    })
                }
                Pair(requestParams, metro.toItemCard())
            } else {
                val card = repository.getPageCardData(request.url, request.params)
                val apiRequest = card.list?.last()?.card_data?.body?.api_request
                val paramsField = apiRequest?.params?.mapValues { it.value.jsonPrimitive.content }
                val requestParams = paramsField?.let {
                    PageRequest(url = apiRequest.url,
                        params = HashMap<String, Any>().apply {
                            putAll(paramsField)
                        })
                } ?: card.last_item_id.takeIf { it.isNotEmpty() }?.let { lastItemId ->
                    PageRequest(url = request.url,
                        params = request.params.also {
                            it[LAST_ITEM_ID] = lastItemId
                        })
                }
                Pair(requestParams, card.toItemCard())
            }
            LoadResult.Page(data = pair.second, prevKey = null, nextKey = pair.first)
        } catch (e: Exception) {
            Log.e("huqiang", "load: $e")
            LoadResult.Error(e)
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
        const val CALL_METRO_LIST = "call_metro_list"
    }
}

