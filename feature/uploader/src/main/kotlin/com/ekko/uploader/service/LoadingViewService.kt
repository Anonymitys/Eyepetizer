package com.ekko.uploader.service

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.uploader.viewmodel.UIState
import com.ekko.uploader.viewmodel.UploaderViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class LoadingViewService @Inject constructor(
    activity: FragmentActivity,
    private val containerTree: ContainerTree,
    pageLoadService: PageLoadService
) {
    private val model by activity.viewModels<UploaderViewModel>()
    private val errorView
        get() = containerTree.binding.errorView

    init {
        activity.lifecycleScope.launch {
            model.uiState.collectLatest {
                when (it) {
                    is UIState.Loading -> {
                        errorView.setLoading()
                    }

                    is UIState.Error -> {
                        errorView.bindErrorMsg(it.message) {
                            pageLoadService.reload()
                        }
                    }

                    is UIState.Success -> {
                        errorView.setComplete()
                    }
                }
            }
        }
    }
}