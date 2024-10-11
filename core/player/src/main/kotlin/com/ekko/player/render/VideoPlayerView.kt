package com.ekko.player.render

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import com.ekko.play.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val player = ExoPlayer.Builder(context).build()

    private val playerView: PlayerView by lazy {
        findViewById<PlayerView?>(R.id.video_view).also {
            it.player = player
        }
    }

    private val _playerState = MutableStateFlow(PlayState.Idle)


    val playState = _playerState.asStateFlow()

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

    init {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
        LayoutInflater.from(context).inflate(R.layout.video_player_view, this)
        player.addListener(listener)
    }


    fun play() {
        player.play()
    }


    fun pause() {
        player.pause()
    }

    @OptIn(UnstableApi::class)
    fun setMediaSource(mediaSource: MediaSource) {
        player.setMediaSource(mediaSource)
        player.playWhenReady = true
        player.prepare()
    }


    fun release() {
        playerView.player = null
        player.removeListener(listener)
        player.release()
    }


}

enum class PlayState {
    Idle, Playing, Paused, Completed
}