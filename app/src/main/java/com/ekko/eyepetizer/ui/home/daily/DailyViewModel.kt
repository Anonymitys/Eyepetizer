package com.ekko.eyepetizer.ui.home.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

/**
 * @Author Ekkoe
 * @Date 2023/1/31 11:26
 */
class DailyViewModel : ViewModel() {
    val flow = Pager(PagingConfig(22)) {
        DailyPagingSource()
    }.flow.cachedIn(viewModelScope)
}