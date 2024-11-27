package com.ekko.base.ktx

import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException

val Context.versionName: String
    get() {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName?:""
        } catch (e: NameNotFoundException) {
            ""
        }
    }

val Context.versionCode: Long
    get() {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionCode.toLong()
        } catch (e: NameNotFoundException) {
            0
        }
    }