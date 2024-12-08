package com.ekko.base.ktx

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.content.res.Configuration
import android.util.Log
import androidx.core.view.DisplayCutoutCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.core.ExperimentalWindowApi
import androidx.window.layout.WindowMetricsCalculator

val Context.versionName: String
    get() {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName ?: ""
        } catch (e: NameNotFoundException) {
            ""
        }
    }

val Context.versionCode: Long
    get() {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.longVersionCode
        } catch (e: NameNotFoundException) {
            0
        }
    }

val Context.isDark: Boolean
    get() {
        val uiMode = resources.configuration.uiMode
        return uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }


val Context.screenWidth: Int
    get() {
        if (this is Activity) {
            val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
            return metrics.bounds.width()
        }
        return resources.displayMetrics.widthPixels
    }

val Context.screenHeight: Int
    get() {
        if (this is Activity) {
            val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
            return metrics.bounds.height()
        }
        return resources.displayMetrics.heightPixels

    }

@OptIn(ExperimentalWindowApi::class)
val Activity.statusBarHeight: Int
    get() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val insets = metrics.getWindowInsets()
            .getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())
        Log.e("huqiang", "statusBarHeight:${insets.top} ")
        return insets.top
    }

@OptIn(ExperimentalWindowApi::class)
val Context.displayCutout: DisplayCutoutCompat?
    get() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val displayCutout = metrics.getWindowInsets().displayCutout
        Log.e("huqiang", "displayCutout: $displayCutout")
        return displayCutout
    }

@OptIn(ExperimentalWindowApi::class)
val Context.isDisplayCutout: Boolean
    get() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        return metrics.getWindowInsets().displayCutout != null
    }


