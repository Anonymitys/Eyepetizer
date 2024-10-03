package com.ekko.playdetail.service

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.ui.PlayerView
import com.ekko.base.ktx.screenHeight
import com.ekko.base.ktx.screenWidth
import com.ekko.base.ktx.statusBarHeight
import com.ekko.playdetail.model.Arguments
import com.ekko.player.render.PlayState
import com.ekko.repository.model.VideoItemCard
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min


@FragmentScoped
class VideoPlayer @Inject constructor(
    private val arguments: Arguments,
    private val fragment: Fragment,
    private val containerViewTree: ContainerViewTree,
    private val configurationService: ConfigurationService,
    private val activity: Activity,
) {

    val playerView = containerViewTree.binding.playerView
    val collapse = containerViewTree.binding.collapse
    val statusBarHeight by lazy {
        fragment.requireActivity().statusBarHeight
    }

    private val windowInsetsController =
        WindowCompat.getInsetsController(
            fragment.requireActivity().window,
            fragment.requireActivity().window.decorView
        ).also {
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    val playState: StateFlow<PlayState>
        get() = playerView.player().playState

    private val callback = object : FragmentLifecycleCallbacks() {
        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            playerView.player().pause()
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            playerView.player().play()
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            playerView.player().release()
        }
    }

    private val fullScreenClickListener = PlayerView.FullscreenButtonClickListener {
        fragment.lifecycleScope.launch {
            activity.requestedOrientation =
                if (it) ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

    }


    init {
        // fastPlay()
        fragment.lifecycleScope.launch {
            try {
                fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(callback, false)
                awaitCancellation()
            } finally {
                fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
            }
        }

        fragment.lifecycleScope.launch {
            configurationService.orientationFlow.collectLatest {
                fullScreen(it)
                updateViewPort(it)
            }
        }
    }


    @OptIn(UnstableApi::class)
    fun fastPlay() {
        arguments.playUrl.takeIf { it.isNotEmpty() }?.let {
            val mediaSource =
                DefaultMediaSourceFactory(fragment.requireContext()).createMediaSource(
                    MediaItem.fromUri(it)
                )
            playerView.player().setMediaSource(mediaSource)
        }
    }

    @OptIn(UnstableApi::class)
    fun updateMediaSource(itemCard: VideoItemCard) {
        val list = itemCard.video?.play_info?.map {
            DefaultMediaSourceFactory(fragment.requireContext()).createMediaSource(
                MediaItem.fromUri(it.url)
            )
        }.takeIf {
            !it.isNullOrEmpty()
        } ?: listOf(
            DefaultMediaSourceFactory(fragment.requireContext()).createMediaSource(
                MediaItem.fromUri(itemCard.video?.play_url ?: "")
            )
        )
        val mediaSource = MergingMediaSource(true, true, *list.toTypedArray())
        playerView.player().setMediaSource(mediaSource)
    }


    fun playCurrent() {
        playerView.player().play()
    }

    private fun updateViewPort(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                val width = max(
                    fragment.requireActivity().screenWidth,
                    fragment.requireActivity().screenHeight
                )
                val height = min(
                    fragment.requireActivity().screenWidth,
                    fragment.requireActivity().screenHeight
                )
                playerView.player().updatePadding(top = 0)
                collapse.layoutParams = AppBarLayout.LayoutParams(
                    width,
                    height
                )
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                val width = min(
                    fragment.requireActivity().screenWidth,
                    fragment.requireActivity().screenHeight
                )
                val height = width * 9 / 16
                playerView.player().updatePadding(top = statusBarHeight)
                collapse.layoutParams = AppBarLayout.LayoutParams(
                    width,
                    height
                )

            }

            else -> {

            }
        }

    }

    private fun fullScreen(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            }
        }

    }
}

