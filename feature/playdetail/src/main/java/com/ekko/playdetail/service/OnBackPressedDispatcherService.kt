package com.ekko.playdetail.service

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ekko.playdetail.alias.BackPressedCallback
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class OnBackPressedDispatcherService @Inject constructor(fragment: Fragment) {
    private val list = mutableListOf<BackPressedCallback>()
    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val result = list.map { it.invoke() }
            if (!result.any { it }) {
                fragment.parentFragmentManager.popBackStack()
            }
        }

    }

    init {
        fragment.requireActivity().onBackPressedDispatcher.addCallback(fragment, callback)
    }

    fun addCallback(callback: BackPressedCallback) {
        list.add(callback)
    }

    fun removeCallback(callback: BackPressedCallback) {
        list.remove(callback)
    }
}
