package com.ekko.playdetail.service

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.ekko.base.ktx.deviceHeight
import com.ekko.base.ktx.deviceWidth
import com.ekko.base.ktx.displayCutout
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


    private val observer = object : DefaultLifecycleObserver {

        override fun onResume(owner: LifecycleOwner) {
            playerView.player().play()
        }

        override fun onPause(owner: LifecycleOwner) {
            playerView.player().pause()
        }

        override fun onDestroy(owner: LifecycleOwner) {
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
                activity.lifecycle.addObserver(observer)
                awaitCancellation()
            } finally {
                activity.lifecycle.removeObserver(observer)
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
        val mediaItem = itemCard.video?.play_info?.firstOrNull()?.let {
            MediaItem.fromUri(it.url)
        } ?: MediaItem.fromUri(itemCard.video?.play_url ?: "")
        playerView.player().setMediaItem(mediaItem)
    }


    fun playCurrent() {
        playerView.player().play()
    }

    private fun updateViewPort(orientation: Int) {
        val deviceWidth = activity.deviceWidth
        val deviceHeight = activity.deviceHeight
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                val width = max(
                    deviceWidth,
                    deviceHeight
                )
                val height = min(
                    deviceWidth,
                    deviceHeight
                )
                collapse.updateLayoutParams<AppBarLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                val width = min(
                    deviceWidth,
                    deviceHeight
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

