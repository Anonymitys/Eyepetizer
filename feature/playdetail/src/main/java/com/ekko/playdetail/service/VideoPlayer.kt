package com.ekko.playdetail.service

import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import com.ekko.repository.model.VideoItemCard
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import javax.inject.Inject


@FragmentScoped
class VideoPlayer @Inject constructor(
    private val fragment: Fragment,
    containerViewTree: ContainerViewTree
) {
    private val player = ExoPlayer.Builder(fragment.requireContext()).build()
    val playerView = containerViewTree.binding.playerView.also { it.player = player }

    init {
        val callback = object : FragmentLifecycleCallbacks() {
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

        fragment.lifecycleScope.launch {
            try {
                fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(callback, false)
                awaitCancellation()
            } finally {
                fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
            }
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
}