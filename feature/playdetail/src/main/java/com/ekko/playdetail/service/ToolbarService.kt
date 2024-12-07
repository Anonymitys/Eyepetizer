package com.ekko.playdetail.service

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.base.ktx.isDark
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class ToolbarService @Inject constructor(
    containerVIewTree: ContainerViewTree,
    private val activity: FragmentActivity,
    private val appLayoutConfigureService: AppLayoutConfigureService,
    private val statusBarService: StatusBarService,
    private val player: VideoPlayer,
) {
    private val toolbar = containerVIewTree.binding.toolBar
    private val playNow = containerVIewTree.binding.playNow


    init {
        navigationIconTint(false)
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
                playNow.isVisible = it
                //只有在非暗黑模式下才需要单独处理相关颜色
                if (activity.isDark.not()) {
                    statusBarService.lightTheme(it)
                    navigationIconTint(it)
                }
            }
        }
    }

    private fun navigationIconTint(collapse: Boolean) {
        val color = MaterialColors.getColor(
            toolbar,
            if (collapse) com.google.android.material.R.attr.colorOnSurface
            else if (activity.isDark) com.google.android.material.R.attr.colorOnSurface
            else com.google.android.material.R.attr.colorSurface
        )
        toolbar.setNavigationIconTint(color)
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