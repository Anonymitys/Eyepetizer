package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.pagedata.DataKey
import com.ekko.playdetail.pagedata.VideoPageData
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@FragmentScoped
class PageLoaderService @Inject constructor(
    private val arguments: Arguments,
    private val fragment: Fragment,
    private val videoDetailRepo: VideoDetailRepo,
    private val recommendRepo: RecommendRepo
) {
    private val _uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    val uiState = _uiStateFlow.asStateFlow()

    init {
        fragment.lifecycleScope.launch {
            load(arguments)
        }
    }


    private suspend fun load(arguments: Arguments) {
        _uiStateFlow.emit(UiState.Loading)
        try {
            val data = VideoPageData()
            data[DataKey.VideoDetail] = videoDetailRepo.load(arguments)
            data[DataKey.Recommend] = recommendRepo.load(arguments)
            _uiStateFlow.emit(UiState.Success(data))
        } catch (e: Exception) {
            _uiStateFlow.emit(UiState.Error(e))
        }
    }


    fun reLoad() {
        fragment
            .lifecycleScope
            .launch {
                load(arguments)
            }
    }
}


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: VideoPageData) : UiState()
    data class Error(val error: Throwable) : UiState()

}