package com.ekko.page.viewmodel

import androidx.lifecycle.ViewModel
import com.ekko.page.ktx.toItemCard
import com.ekko.page.model.ItemCard
import com.ekko.repository.PageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ScrollListViewModel @Inject constructor(private val repository: PageRepository) :
    ViewModel() {

    fun page(pageType: String, pageLabel: String) = flow {
        try {
            emit(UIState.Loading)
            val params = mutableMapOf<String, Any>(
                PageRepository.PAGE_TYPE to pageType,
                PageRepository.PAGE_LABEL to pageLabel
            )
            val data = repository.getPageCardData(PageRepository.GET_PAGE_URL, params).toItemCard()
            emit(if (data.isEmpty()) UIState.Empty else UIState.Success(data))
        } catch (e: Exception) {
            emit(UIState.Error(e.message ?: "Unknown Error"))
        }
    }
}

sealed class UIState {
    data object Loading : UIState()
    data class Success(val data: List<ItemCard>) : UIState()
    data class Error(val message: String) : UIState()
    data object Empty : UIState()
}