package com.ekko.page.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ekko.page.paing3.PageRequest
import com.ekko.page.paing3.PagingSource
import com.ekko.repository.PageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/27 11:55
 */

@HiltViewModel
class PageViewModel @Inject constructor(private val repository: PageRepository) : ViewModel() {

    fun getPageData(
        pageType: String,
        pageLabel: String,
    ) = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        PagingSource(
            repository, PageRequest(
                url = PageRepository.GET_PAGE_URL,
                mutableMapOf(
                    PageRepository.PAGE_TYPE to pageType,
                    PageRepository.PAGE_LABEL to pageLabel
                )
            )
        )
    }.flow.cachedIn(viewModelScope)
}
