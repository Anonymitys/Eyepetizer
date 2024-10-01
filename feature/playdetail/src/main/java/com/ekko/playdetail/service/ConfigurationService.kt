package com.ekko.playdetail.service

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@FragmentScoped
class ConfigurationService @Inject constructor(fragment: Fragment, context: Context) {

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
        fragment.lifecycleScope.launch {
            try {
                context.registerComponentCallbacks(callback)
                awaitCancellation()
            } finally {
                context.unregisterComponentCallbacks(callback)
            }
        }
    }
}