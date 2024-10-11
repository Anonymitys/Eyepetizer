package com.ekko.player

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.FrameLayout
import com.ekko.play.R
import com.ekko.player.render.VideoPlayerView

class VideoPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var playerView: VideoPlayerView

    init {
        LayoutInflater.from(context).inflate(R.layout.eyepetizer_player_view, this)
        playerView = findViewById(R.id.hello_view)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      //  Log.e(TAG, "onMeasure: $widthMeasureSpec $heightMeasureSpec")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.e(TAG, "onConfigurationChanged: $newConfig", )
        super.onConfigurationChanged(newConfig)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
       // Log.e(TAG, "onApplyWindowInsets: $insets")
        return super.onApplyWindowInsets(insets)
    }

    fun player(): VideoPlayerView = playerView


    companion object {
        private const val TAG = "VideoPanel"
    }
}