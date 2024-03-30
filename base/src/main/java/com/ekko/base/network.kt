package com.ekko.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

val Context.networkName: String
    get() {
        val connectMgr by lazy {
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        }
        val nc = connectMgr?.getNetworkCapabilities(connectMgr?.activeNetwork) ?: return "NONE"
        return when {
            nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
            nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "MOBILE"
            else -> "NONE"
        }
    }