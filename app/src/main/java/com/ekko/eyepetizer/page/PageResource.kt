package com.ekko.eyepetizer.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.repository.IPageRepository
import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro
import com.ekko.repository.model.MetroCard
import kotlinx.serialization.json.jsonPrimitive

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/27 15:28
 */
class PageResource(
    private val repository: IPageRepository,
    private val pageRequest: PageRequest,
) : PagingSource<PageRequest, ItemCard>() {

    override suspend fun load(params: LoadParams<PageRequest>): LoadResult<PageRequest, ItemCard> {
        val request = params.key ?: pageRequest
        return try {
            val pair = if (request.url.contains(CALL_METRO_LIST)) {
                val metro = repository.getPageMetroData(request.url, request.params)
                Pair(
                    PageRequest(request.url, HashMap<String, Any>().apply {
                        putAll(request.params)
                        metro.last_item_id.takeIf { it > 0 }?.let { put(LAST_ITEM_ID, it) }
                    }), getList(metro = metro)
                )

            } else {
                val card = repository.getPageCardData(request.url, request.params)
                val apiRequest = card.list?.last()?.card_data?.body?.api_request
                val paramsField = apiRequest?.params?.mapValues { it.value.jsonPrimitive.content }
                    ?: request.params
                Pair(
                    PageRequest(url = apiRequest?.url ?: request.url,
                        params = HashMap<String, Any>().apply {
                            putAll(paramsField)
                            card.last_item_id.takeIf { it > 0 }?.let { put(LAST_ITEM_ID, it) }
                        }),
                    getList(card = card)
                )
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

    private fun getList(metro: Metro? = null, card: CardList? = null): List<ItemCard> {
        return card?.list?.filter { it.card_data?.body?.api_request == null }?.flatMap {
            if (it.interaction?.scroll == Scroll.HORIZONTAL) {
                listOf(
                    ItemCard(
                        it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                        Scroll.HORIZONTAL,
                        it.card_data?.body?.metro_list ?: listOf()
                    )
                )
            } else {
                it.card_data?.body?.metro_list?.map { metroCard ->
                    ItemCard(
                        metroCard.style?.tpl_label ?: "", Scroll.VERTICAL, listOf(metroCard)
                    )
                } ?: listOf()
            }
        } ?: metro?.item_list?.map {
            ItemCard(
                it.style?.tpl_label ?: "",
                Scroll.VERTICAL,
                listOf(it)
            )
        }
        ?: listOf()
    }

    companion object {
        const val LAST_ITEM_ID = "last_item_id"
        const val CALL_METRO_LIST = "call_metro_list"
    }
}


data class ItemCard(
    val type: String,
    val scroll: String,
    val data: List<MetroCard>,
)