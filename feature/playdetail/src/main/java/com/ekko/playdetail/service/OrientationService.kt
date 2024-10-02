package com.ekko.playdetail.service

import android.content.res.Configuration
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@FragmentScoped
class OrientationService @Inject constructor() {
    private val _orientationFlow = MutableStateFlow<Int>(Configuration.ORIENTATION_PORTRAIT)

    val orientationFlow
        get() = _orientationFlow.asStateFlow()


    fun orientation(orientation: Int) {
        _orientationFlow.value = orientation
    }
}