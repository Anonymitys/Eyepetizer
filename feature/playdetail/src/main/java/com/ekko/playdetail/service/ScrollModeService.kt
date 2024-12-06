package com.ekko.playdetail.service

import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.ekko.player.render.PlayState
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class ScrollModeService @Inject constructor(
    containerViewTree: ContainerViewTree,
    private val player: VideoPlayer,
    private val activity: FragmentActivity,
    private val configurationService: ConfigurationService
) {

    private val collapse = containerViewTree.binding.collapse


    init {
        activity.lifecycleScope.launch {
            player.playState.combine(configurationService.orientationFlow) { state, orientation ->
                Pair(state, orientation)
            }.collectLatest {
                isScrollEnable =
                    it.first == PlayState.Paused && it.second == Configuration.ORIENTATION_PORTRAIT
            }
        }
    }

    private var isScrollEnable: Boolean
        get() {
            val lp = collapse.layoutParams as AppBarLayout.LayoutParams
            return lp.scrollFlags and AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL != 0 && lp.scrollFlags and AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED != 0
        }
        set(enable) {
            val lp = collapse.layoutParams as AppBarLayout.LayoutParams
            if (enable) {
                lp.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            } else {
                lp.scrollFlags = 0
            }
        }

}