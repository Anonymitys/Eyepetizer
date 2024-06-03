package com.ekko.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

inline fun <reified F : Fragment> FragmentManager.showFragment(
    @IdRes containerViewId: Int,
    tag: String? = null,
    args: Bundle? = null
) {
    beginTransaction()
        .replace(containerViewId, F::class.java, args, tag)
        .commitAllowingStateLoss()
}

fun FragmentManager.hide(@IdRes containerViewId: Int) {
    val fragment = findFragmentById(containerViewId) ?: return
    beginTransaction().remove(fragment).commitAllowingStateLoss()
}

fun FragmentManager.hide(fragment: Fragment) {
    beginTransaction().remove(fragment).commitAllowingStateLoss()
}