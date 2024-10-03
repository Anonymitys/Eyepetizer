package com.ekko.base.ktx

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.window.core.ExperimentalWindowApi
import androidx.window.layout.WindowMetricsCalculator

val Context.screenWidth: Int
    get() {
        val wm by lazy(LazyThreadSafetyMode.NONE) {
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

val Context.screenHeight: Int
    get() {
        val wm by lazy(LazyThreadSafetyMode.NONE) {
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

val Activity.screenWidth: Int
    get() {
        val metrics by lazy {
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        }
        return metrics.bounds.width()
    }

val Activity.screenHeight: Int
    get() {
        val metrics by lazy {
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        }
        return metrics.bounds.height()
    }

@OptIn(ExperimentalWindowApi::class)
val Activity.statusBarHeight: Int
    get() {
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val insets = metrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())
        Log.e("huqiang", "statusBar: $insets", )
        return insets.top
    }



