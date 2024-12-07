package com.ekko.player

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
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

    private val applyWindowInsetsListener =
        androidx.core.view.OnApplyWindowInsetsListener { v, insets ->
            Log.e(TAG, "onApplyWindowInsets: $insets")
            insets
        }


    init {
        ViewCompat.setOnApplyWindowInsetsListener(this, applyWindowInsetsListener)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //  Log.e(TAG, "onMeasure: $widthMeasureSpec $heightMeasureSpec")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.e(TAG, "onConfigurationChanged: $newConfig")
        super.onConfigurationChanged(newConfig)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        // Log.e(TAG, "onApplyWindowInsets: $insets")
        return super.onApplyWindowInsets(insets)
    }

    fun player(): VideoPlayerView = binding.videoPlayerView


    companion object {
        private const val TAG = "VideoPanel"
    }
}