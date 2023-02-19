package com.ekko.eyepetizer.ui.home.daily

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.repository.api.core.apiService
import com.ekko.repository.model.Item

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/31 11:43
 */
class DailyPagingSource : PagingSource<String, Item>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        return try {
            val dailyCard = apiService.homeApi.getDaily(
                if (params.key.isNullOrEmpty()) "http://baobab.kaiyanapp.com/api/v5/index/tab/feed" else params.key!!
            )
            if (dailyCard.itemList.isNullOrEmpty()) {
                LoadResult.Error(RuntimeException("item empty"))
            } else {
                LoadResult.Page(
                    data = dailyCard.itemList!!.filter { it.type != "textCard" }, prevKey = null, nextKey = dailyCard.nextPageUrl
                )
            }
        } catch (e: Exception) {
            Log.e("huqiang", "load: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Item>): String? {
        return null
    }
}