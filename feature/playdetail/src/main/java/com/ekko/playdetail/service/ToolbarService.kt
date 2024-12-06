package com.ekko.playdetail.service

import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class ToolbarService @Inject constructor(
    containerVIewTree: ContainerViewTree,
    activity: FragmentActivity,
    private val appLayoutConfigureService: AppLayoutConfigureService,
    private val player: VideoPlayer,
) {
    private val toolbar = containerVIewTree.binding.toolBar
    private val playNow = containerVIewTree.binding.playNow


    init {
        toolbar.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

        activity.lifecycleScope.launch {
            callbackFlow<View> {
                playNow.setOnClickListener {
                    trySend(it)
                }
                awaitClose { Log.d(TAG, "awaitClose") }
            }.collectLatest {
                playCurrent()
            }
        }

        activity.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                val color = MaterialColors.getColor(
                    toolbar,
                    if (it) com.google.android.material.R.attr.colorOnSurface else com.google.android.material.R.attr.colorSurface
                )
                navigationIconTint(color)
                playNow.isVisible = it
            }
        }
    }

    private fun navigationIconTint(@ColorInt navigationIconTint: Int) {
        toolbar.setNavigationIconTint(navigationIconTint)
    }

    private suspend fun playCurrent() {
        appLayoutConfigureService.setExpanded(true)
        appLayoutConfigureService.awaitCompleteExpand()
        player.playCurrent()
    }


    companion object {
        private const val TAG = "ToolbarService"
    }

}