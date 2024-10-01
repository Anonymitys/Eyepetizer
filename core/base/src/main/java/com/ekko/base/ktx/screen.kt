package com.ekko.base.ktx

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
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
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)

        return metrics.bounds.height()
    }


