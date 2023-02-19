package com.ekko.repository

import android.app.Application

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/30 17:52
 */
object EyepetizerContext {
    private lateinit var application: Application

    @JvmStatic
    fun attachApplication(app: Application) {
        application = app
    }

    @JvmStatic
    fun application(): Application {
        return application
    }
}