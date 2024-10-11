package com.ekko.playdetail.service

import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.lifecycleScope
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

    init {
        statusBar(true)
        val callback = object : FragmentLifecycleCallbacks() {
            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                statusBar(false)
            }
        }
        fragment.lifecycleScope.launch {
            try {
                fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(callback, false)
                awaitCancellation()
            } finally {
                fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
            }
        }

        fragment.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                statusBar(!it)
            }
        }
    }

    private fun statusBar(dark: Boolean) {
   //     windowInsetsController.isAppearanceLightStatusBars = !dark
//        activity.window.statusBarColor = ContextCompat.getColor(
//            activity,
//            if (dark) android.R.color.black else android.R.color.transparent
//        )
    }
}