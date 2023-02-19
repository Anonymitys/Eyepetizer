package com.ekko.eyepetizer.ui.userinfo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ekko.repository.api.core.apiService
import com.ekko.repository.model.Item

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/3 18:13
 */
class UploaderWorksPagingSource(private val url: String) : PagingSource<String, Item>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        return try {
            val dailyCard = apiService.uploaderApi.getUploaderWorks(
                if (params.key.isNullOrEmpty()) url else params.key!!
            )
            if (dailyCard.itemList.isNullOrEmpty()) {
                LoadResult.Error(RuntimeException("item empty"))
            } else {
                LoadResult.Page(
                    data = dailyCard.itemList!!, prevKey = null,
                    nextKey = dailyCard.nextPageUrl
                )
            }
        } catch (e: Exception) {
            Log.e("huqiang", "load: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<kotlin.String, com.ekko.repository.model.Item>): kotlin.String? {
        return null
    }
}