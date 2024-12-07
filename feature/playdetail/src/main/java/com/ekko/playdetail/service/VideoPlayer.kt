package com.ekko.playdetail.service

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.ui.PlayerView
import com.ekko.base.ktx.screenHeight
import com.ekko.base.ktx.screenWidth
import com.ekko.base.ktx.statusBarHeight
import com.ekko.player.render.PlayState
import com.ekko.repository.model.VideoItemCard
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min


@ActivityScoped
class VideoPlayer @Inject constructor(
    private val containerViewTree: ContainerViewTree,
    private val configurationService: ConfigurationService,
    private val activity: FragmentActivity,
) {

    val playerView = containerViewTree.binding.playerView
    val collapse = containerViewTree.binding.collapse
    val statusBarHeight by lazy {
        activity.statusBarHeight
    }

    private val windowInsetsController =
        WindowCompat.getInsetsController(
            activity.window,
            activity.window.decorView
        ).also {
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    val playState: StateFlow<PlayState>
        get() = playerView.player().playState

    private val callback = object : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            //
        }

        override fun onActivityStarted(activity: Activity) {
            //
        }

        override fun onActivityResumed(activity: Activity) {
            playerView.player().play()
        }

        override fun onActivityPaused(activity: Activity) {
            playerView.player().pause()
        }

        override fun onActivityStopped(activity: Activity) {
            //
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            //
        }

        override fun onActivityDestroyed(activity: Activity) {
            playerView.player().release()
        }
    }

    private val fullScreenClickListener = PlayerView.FullscreenButtonClickListener {
        activity.lifecycleScope.launch {
            activity.requestedOrientation =
                if (it) ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

    }


    init {
        // fastPlay()
        activity.lifecycleScope.launch {
            try {
                activity.registerActivityLifecycleCallbacks(callback)
                awaitCancellation()
            } finally {
                activity.unregisterActivityLifecycleCallbacks(callback)
            }
        }

        activity.lifecycleScope.launch {
            configurationService.orientationFlow.collectLatest {
                fullScreen(it)
                updateViewPort(it)
            }
        }
    }


//    @OptIn(UnstableApi::class)
//    fun fastPlay() {
//        arguments.playUrl.takeIf { it.isNotEmpty() }?.let {
//            val mediaSource =
//                DefaultMediaSourceFactory(fragment.requireContext()).createMediaSource(
//                    MediaItem.fromUri(it)
//                )
//            playerView.player().setMediaSource(mediaSource)
//        }
//    }

    @OptIn(UnstableApi::class)
    fun updateMediaSource(itemCard: VideoItemCard) {
        val list = itemCard.video?.play_info?.map {
            DefaultMediaSourceFactory(activity).createMediaSource(
                MediaItem.fromUri(it.url)
            )
        }.takeIf {
            !it.isNullOrEmpty()
        } ?: listOf(
            DefaultMediaSourceFactory(activity).createMediaSource(
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
                    activity.screenWidth,
                    activity.screenHeight
                )
                val height = min(
                    activity.screenWidth,
                    activity.screenHeight
                )
               // playerView.player().updatePadding(top = 0)
                collapse.layoutParams = AppBarLayout.LayoutParams(
                    width,
                    height
                )
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                val width = min(
                    activity.screenWidth,
                    activity.screenHeight
                )
                val height = width * 9 / 16
             //   playerView.player().updatePadding(top = statusBarHeight)
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

