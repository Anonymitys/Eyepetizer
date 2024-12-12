package com.ekko.uploader.service

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.uploader.di.VideoPageComponentBuilder
import com.ekko.uploader.di.VideoPageComponentEntryPoint
import com.ekko.uploader.viewmodel.UIState
import com.ekko.uploader.viewmodel.UploaderViewModel
import dagger.hilt.EntryPoints
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@ActivityScoped
class PageLoadService @Inject constructor(
    private val activity: FragmentActivity,
    private val intentParseService: IntentParseService,
    private val componentBuilder: VideoPageComponentBuilder
) {
    private val model by activity.viewModels<UploaderViewModel>()

    init {
        activity.lifecycleScope.launch {
            intentParseService.argumentFlow.collect {
                model.load(it.resourceId)
            }
        }

        activity.lifecycleScope.launch {
            model.uiState.collectLatest {
                when (it) {
                    is UIState.Loading -> {

                    }

                    is UIState.Error -> {

                    }

                    is UIState.Success -> {
                        supervisorScope {
                            val graph = componentBuilder.bindCoroutineScope(this)
                                .bindIntentParameter(intentParseService.arguments)
                                .bindVideoPageData(it.data)
                                .build()
                            EntryPoints.get(graph, VideoPageComponentEntryPoint::class.java)
                                .attach()
                            awaitCancellation()
                        }
                    }
                }
            }
        }
    }


    fun reload() {
        activity.lifecycleScope.launch {
            model.load(intentParseService.arguments.resourceId)
        }
    }
}