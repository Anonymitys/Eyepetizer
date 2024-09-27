package com.ekko.playdetail.service

import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@FragmentScoped
class ToolbarService @Inject constructor(
    containerVIewTree: ContainerViewTree,
    activity: FragmentActivity,
    private val appLayoutConfigureService: AppLayoutConfigureService,
    fragment: Fragment,
) {
    private val toolbar = containerVIewTree.binding.toolBar
    private val playNow = containerVIewTree.binding.playNow


    init {
        toolbar.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

        fragment.lifecycleScope.launch {
            callbackFlow<View> {
                playNow.setOnClickListener {
                    trySend(it)
                }
                awaitClose { Log.d(TAG, "awaitClose") }
            }.collectLatest {
                playCurrent()
            }
        }

        fragment.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                val color = ContextCompat.getColor(
                    activity, if (it) android.R.color.black else android.R.color.white
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
    }


    companion object {
        private const val TAG = "ToolbarService"
    }

}