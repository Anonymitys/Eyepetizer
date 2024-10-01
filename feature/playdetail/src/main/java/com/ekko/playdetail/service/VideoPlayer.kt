package com.ekko.playdetail.service

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.ui.PlayerView
import com.ekko.base.ktx.screenHeight
import com.ekko.base.ktx.screenWidth
import com.ekko.playdetail.model.Arguments
import com.ekko.repository.model.VideoItemCard
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val player = ExoPlayer.Builder(fragment.requireContext()).build()
    val playerView = containerViewTree.binding.playerView.also {
        it.player = player
    }

    private val windowInsetsController =
        WindowCompat.getInsetsController(
            fragment.requireActivity().window,
            fragment.requireActivity().window.decorView
        ).also {
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    private val _playerState = MutableStateFlow(PlayState.Idle)


    val playState = _playerState.asStateFlow()

    private val callback = object : FragmentLifecycleCallbacks() {
        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            player.pause()
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            player.play()
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            player.release()
            playerView.player = null
        }
    }

    private val listener = object : Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                _playerState.value = PlayState.Completed
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            if (playWhenReady) {
                _playerState.value = PlayState.Playing
                playerView.keepScreenOn = true
            } else {
                _playerState.value = PlayState.Paused
                playerView.keepScreenOn = false
            }
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
                player.addListener(listener)
                fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(callback, false)
                playerView.setFullscreenButtonClickListener(fullScreenClickListener)
                awaitCancellation()
            } finally {
                player.removeListener(listener)
                fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
                playerView.setFullscreenButtonClickListener(null)
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
            player.setMediaSource(mediaSource)
            player.playWhenReady = true
            player.prepare()
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
        player.setMediaSource(mediaSource)
        player.playWhenReady = true
        player.prepare()
    }


    fun playCurrent() {
        player.play()
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
                playerView.layoutParams = CollapsingToolbarLayout.LayoutParams(
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
                playerView.layoutParams = CollapsingToolbarLayout.LayoutParams(
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

enum class PlayState {
    Idle,
    Playing,
    Paused,
    Completed
}