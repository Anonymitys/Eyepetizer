package com.ekko.playdetail.service

import android.content.res.Configuration
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.base.ktx.isDisplayCutout
import com.ekko.player.render.PlayState
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScoped
class SystemBarsService @Inject constructor(
    private val activity: FragmentActivity,
    private val player: VideoPlayer,
    private val configurationService: ConfigurationService,
    private val toolbarService: ToolbarService
) {

    private val windowInsetsController =
        WindowCompat.getInsetsController(
            activity.window,
            activity.window.decorView
        ).also {
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    init {
        activity.lifecycleScope.launch {
            combine(
                player.controllerVisibilityState,
                configurationService.orientationFlow,
                player.playState
            ) { visibility, orientation, playState ->
                Triple(visibility, orientation, playState)
            }.collectLatest {
                handleSystemBar(it)
                handleToolbar(it)
            }
        }
    }

    private fun handleSystemBar(result: Triple<Int, Int, PlayState>) {
        val visibility = result.first
        val playState = result.third
        when (result.second) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                if (activity.isDisplayCutout || playState != PlayState.Playing || visibility == View.VISIBLE) {
                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                } else {
                    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
                    windowInsetsController.show(WindowInsetsCompat.Type.navigationBars())
                }

            }
        }
    }

    private fun handleToolbar(result: Triple<Int, Int, PlayState>) {
        val visibility = result.first
        val playState = result.third
        if (playState != PlayState.Playing || visibility == View.VISIBLE) {
            toolbarService.showToolBar(true)
        } else {
            toolbarService.showToolBar(false)
        }
    }

}