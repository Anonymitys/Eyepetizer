package com.ekko.player

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.FrameLayout
import com.ekko.base.ktx.displayCutout
import com.ekko.play.databinding.EyepetizerPlayerViewBinding
import com.ekko.player.render.VideoPlayerView

class VideoPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        EyepetizerPlayerViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.displayCutout?.let {
            setPadding(0, it.safeInsetTop,0, 0)
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.e(TAG, "onConfigurationChanged: $newConfig")
        context.displayCutout?.let {
            setPadding(0, it.safeInsetTop,0, 0)
        }
    }

    fun player(): VideoPlayerView = binding.videoPlayerView


    companion object {
        private const val TAG = "VideoPanel"
    }
}