package com.ekko.base.ktx

import android.app.Activity
import android.content.Context
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator

val Context.screenWidth: Int
    get() {
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        return metrics.bounds.width()
    }

val Context.screenHeight: Int
    get() {
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        return metrics.bounds.height()
    }

val Activity.statusBarHeight: Int
    get() {
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val insets = metrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())
//        val insets = WindowInsetsCompat.toWindowInsetsCompat(window.decorView.rootWindowInsets)
//            .getInsets(WindowInsetsCompat.Type.systemBars())
        return insets.top
    }



