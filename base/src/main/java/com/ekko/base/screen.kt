package com.ekko.base

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import androidx.window.layout.WindowMetricsCalculator

val Context.screenWidth: Int
    get() {
        val bounds by lazy(LazyThreadSafetyMode.NONE) {
            WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(activity).bounds
        }
        return bounds.width()
    }

val Context.screenHeight: Int
    get() {
        val metrics by lazy(LazyThreadSafetyMode.NONE) {
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        }
        return metrics.bounds.height()
    }


val Int.dp: Int
    get() {
        return (0.5 + this.times(Resources.getSystem().displayMetrics.density)).toInt()
    }


private val Context.activity: Activity
    get() {
        return when (this) {
            is Activity -> {
                this
            }

            is ContextWrapper -> {
                this.baseContext as Activity
            }

            else -> {
                throw IllegalArgumentException("illegal context")
            }
        }
    }