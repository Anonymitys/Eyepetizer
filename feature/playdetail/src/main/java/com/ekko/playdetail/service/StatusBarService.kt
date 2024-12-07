package com.ekko.playdetail.service

import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class StatusBarService @Inject constructor(
    private val activity: FragmentActivity
) {
    private val windowInsetsController = WindowCompat.getInsetsController(
        activity.window, activity.window.decorView
    )

    fun lightTheme(light: Boolean) {
        windowInsetsController.isAppearanceLightStatusBars = light
    }
}