package com.ekko.repository

import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro
import com.ekko.repository.model.MetroList

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/27 11:43
 */
interface IPageRepository {

    suspend fun getPageCardData(
        url: String,
        params: MutableMap<String, Any>,
    ): CardList

    suspend fun getPageMetroData(
        url: String,
        params: MutableMap<String, Any>,
    ): Metro

    companion object {
        const val PAGE_TYPE = "page_type"
        const val PAGE_LABEL = "page_label"
        const val GET_PAGE_URL = "http://api.eyepetizer.net/v1/card/page/get_page"
    }
}