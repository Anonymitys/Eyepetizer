package com.ekko.uploader.viewmodel

import androidx.lifecycle.ViewModel
import com.ekko.repository.UserInfoRepository
import com.ekko.repository.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UploaderViewModel @Inject constructor(private val userInfoRepository: UserInfoRepository) :
    ViewModel() {

    private var _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    val uiState
        get() = _uiState.asStateFlow()

    suspend fun load(uid: Long) {
        try {
            val data = userInfoRepository.userInfo(uid)
            _uiState.emit(UIState.Success(data))

        } catch (e: Exception) {
            _uiState.emit(UIState.Error(e.message ?: ""))
        }
    }
}

sealed class UIState {
    data object Loading : UIState()
    data class Success(val data: UserInfo) : UIState()
    data class Error(val message: String) : UIState()
}