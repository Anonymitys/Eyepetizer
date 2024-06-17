package com.ekko.eyepetizer

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/25 15:39
 */

@HiltAndroidApp
class EyepetizerApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}