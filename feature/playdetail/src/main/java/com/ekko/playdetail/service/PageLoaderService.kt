package com.ekko.playdetail.service

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.pagedata.DataKey
import com.ekko.playdetail.pagedata.VideoPageData
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class PageLoaderService @Inject constructor(
    private val activity: FragmentActivity,
    private val videoDetailRepo: VideoDetailRepo,
    private val recommendRepo: RecommendRepo,
    private val intentParseService: IntentParseService,
    private val parameters: IntentParameters
) {
    private val _uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    val uiState = _uiStateFlow.asStateFlow()

    init {
        activity.lifecycleScope.launch {
            intentParseService.argumentFlow.collectLatest {
                load(it)
            }
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
        activity.lifecycleScope
            .launch {
                load(parameters.arguments)
            }
    }
}


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: VideoPageData) : UiState()
    data class Error(val error: Throwable) : UiState()

}