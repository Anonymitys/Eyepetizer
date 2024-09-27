package com.ekko.playdetail.service

import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
class StatusBarService @Inject constructor(
    private val activity: FragmentActivity,
    fragment: Fragment,
    appLayoutConfigureService: AppLayoutConfigureService
) {
    private val windowInsetsController = WindowCompat.getInsetsController(
        activity.window, activity.window.decorView
    )

    private var currentOffset = 0

    init {
        statusBar(true)
        val callback = object : FragmentLifecycleCallbacks() {
            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                statusBar(false)
            }
        }
        val offsetChangedListener =
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                //向下滑动
                val down = currentOffset < verticalOffset
                currentOffset = verticalOffset
                if (100 + verticalOffset > 0 && down) {
                    statusBar(true)
                }
            }

        fragment.lifecycleScope.launch {
            try {
                fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(callback, false)
                appLayoutConfigureService.addOnOffsetChangedListener(offsetChangedListener)
                awaitCancellation()
            } finally {
                fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
                appLayoutConfigureService.removeOnOffsetChangedListener(offsetChangedListener)
            }
        }

        fragment.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                lightTheme(it)
                if (it) {
                    statusBar(false)
                }
            }
        }
    }

    private fun statusBar(dark: Boolean) {
        windowInsetsController.isAppearanceLightStatusBars = !dark
        activity.window.statusBarColor = ContextCompat.getColor(
            activity,
            if (dark) android.R.color.black else android.R.color.transparent
        )
    }

    private fun lightTheme(light: Boolean) {
        windowInsetsController.isAppearanceLightStatusBars = light
    }
}