package com.ekko.uploader.service

import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.base.ktx.isDark
import com.ekko.uploader.di.VideoPageScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@VideoPageScope
class StatusBarService @Inject constructor(
    private val activity: FragmentActivity,
    appLayoutConfigureService: AppLayoutConfigureService
) {
    private val windowInsetsController = WindowCompat.getInsetsController(
        activity.window, activity.window.decorView
    )

    init {
        activity.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                if (activity.isDark.not()) {
                    lightTheme(it)
                }
            }
        }
    }


    fun lightTheme(light: Boolean) {
        windowInsetsController.isAppearanceLightStatusBars = light
    }
}