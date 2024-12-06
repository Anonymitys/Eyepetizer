package com.ekko.playdetail.service

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class ConfigurationService @Inject constructor(activity: FragmentActivity) {

    private val _orientationFlow = MutableStateFlow<Int>(Configuration.ORIENTATION_PORTRAIT)

    val orientationFlow
        get() = _orientationFlow.asStateFlow()

    init {
        val callback = object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                _orientationFlow.value = newConfig.orientation
            }

            override fun onLowMemory() {
                //
            }

        }
        activity.lifecycleScope.launch {
            try {
                activity.registerComponentCallbacks(callback)
                awaitCancellation()
            } finally {
                activity.unregisterComponentCallbacks(callback)
            }
        }
    }
}