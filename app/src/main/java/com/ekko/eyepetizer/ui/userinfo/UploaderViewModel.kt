package com.ekko.eyepetizer.ui.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/15 17:33
 */
class UploaderViewModel : ViewModel() {

    fun getWorkFlow(url: String) = Pager(PagingConfig(5)) {
        UploaderWorksPagingSource(url)
    }.flow.cachedIn(viewModelScope)
}