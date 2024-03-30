package com.ekko.base

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
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
