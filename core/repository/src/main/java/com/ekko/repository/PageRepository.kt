package com.ekko.repository

import com.ekko.api.core.ApiService
import com.ekko.repository.api.PageApi
import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro
import com.ekko.repository.model.MetroList
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonNull
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 16:49
 */
class PageRepository @Inject constructor(apiService: ApiService) {

    private val pageApi = apiService.createApi<PageApi>()

    suspend fun getPageCardData(
        url: String,
        params: MutableMap<String, Any>,
    ): CardList {
        return pageApi.getPageCardData(
            url.ifEmpty { GET_PAGE_URL }, params
        )
    }

    suspend fun getPageMetroData(
        url: String,
        params: MutableMap<String, Any>,
    ): Metro {
        return pageApi.getPageMetroData(
            url.ifEmpty { GET_PAGE_URL }, params
        )
    }

    companion object {
        const val PAGE_TYPE = "page_type"
        const val PAGE_LABEL = "page_label"
        const val GET_PAGE_URL = "http://api.eyepetizer.net/v1/card/page/get_page"
    }
}