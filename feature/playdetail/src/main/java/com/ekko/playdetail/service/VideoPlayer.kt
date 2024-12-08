package com.ekko.playdetail.service

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.ui.PlayerView
import com.ekko.base.ktx.displayCutout
import com.ekko.base.ktx.screenHeight
import com.ekko.base.ktx.screenWidth
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

    private val playerView
        get() = containerViewTree.binding.playerView
    private val collapse
        get() = containerViewTree.binding.collapse

    val playState: StateFlow<PlayState>
        get() = playerView.player().playState

    val controllerVisibilityState
        get() = playerView.player().controllerVisibilityState


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
                collapse.updateLayoutParams<AppBarLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                val width = min(
                    activity.screenWidth,
                    activity.screenHeight
                )
                val height = width * 9 / 16
                collapse.updateLayoutParams<AppBarLayout.LayoutParams> {
                    this.width = width
                    this.height =
                        height + (activity.displayCutout?.safeInsetTop
                            ?: 0)
                }
            }

            else -> {

            }
        }

    }
}

