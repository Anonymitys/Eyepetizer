package com.ekko.base.ktx

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

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
