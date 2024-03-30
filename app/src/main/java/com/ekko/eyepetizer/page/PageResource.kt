package com.ekko.eyepetizer.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.repository.IPageRepository
import com.ekko.repository.model.CardList
import com.ekko.repository.model.Layout
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
                val requestParams = metro.last_item_id.takeIf { it > 0 }?.let {
                    PageRequest(request.url, HashMap<String, Any>().apply {
                        putAll(request.params)
                        put(LAST_ITEM_ID, it)
                    })
                }
                Pair(requestParams, getList(metro = metro))
            } else {
                val card = repository.getPageCardData(request.url, request.params)
                val apiRequest = card.list?.last()?.card_data?.body?.api_request
                val paramsField = apiRequest?.params?.mapValues { it.value.jsonPrimitive.content }
                val requestParams = paramsField?.let {
                    PageRequest(url = apiRequest.url,
                        params = HashMap<String, Any>().apply {
                            putAll(paramsField)
                        })
                } ?: card.last_item_id.takeIf { it > 0 }?.let { lastItemId ->
                    PageRequest(url = request.url,
                        params = request.params.also {
                            it[LAST_ITEM_ID] = lastItemId
                        })
                }
                Pair(requestParams, getList(card = card))
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

    private fun getList(
        metro: Metro? = null,
        card: CardList? = null
    ): List<ItemCard> {
        return card?.list?.filter { it.card_data?.body?.api_request == null }?.flatMap {
            val bodyList = if (it.interaction?.scroll == Scroll.HORIZONTAL) {
                listOf(
                    ItemCard(
                        it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                        Scroll.HORIZONTAL,
                        it.card_data?.header,
                        it.card_data?.body?.metro_list ?: listOf()
                    )
                )
            } else {
                it.card_data?.body?.metro_list?.map { metroCard ->
                    ItemCard(
                        metroCard.style?.tpl_label ?: "", Scroll.VERTICAL, it.card_data?.header,
                        listOf(metroCard)
                    )
                } ?: listOf()
            }
            bodyList
        } ?: metro?.item_list?.map {
            ItemCard(
                it.style?.tpl_label ?: "",
                Scroll.VERTICAL,
                null,
                listOf(it)
            )
        }
        ?: listOf()
    }

    companion object {
        const val LAST_ITEM_ID = "last_item_id"
        const val CALL_METRO_LIST = "call_metro_list"
        const val GET_PAGE = "card/page/get_page"
    }
}

data class ItemCard(
    val type: String,
    val scroll: String,
    val header: Layout?,
    val data: List<MetroCard>,
)