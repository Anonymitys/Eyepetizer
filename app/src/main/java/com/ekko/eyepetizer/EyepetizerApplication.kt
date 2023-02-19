package com.ekko.eyepetizer

import android.annotation.SuppressLint
import android.app.Application
import com.ekko.repository.EyepetizerContext
import com.google.android.material.color.DynamicColors

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/30 17:35
 */

class EyepetizerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        EyepetizerContext.attachApplication(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}