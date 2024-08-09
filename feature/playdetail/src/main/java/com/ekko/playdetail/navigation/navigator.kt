package com.ekko.playdetail.navigation

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ekko.playdetail.ui.PlayDetailFragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform

fun FragmentManager.navigateTo(@IdRes id: Int, shareElement: View, fragment: PlayDetailFragment) {
    ViewCompat.setTransitionName(shareElement,"shared_element_container")
    fragment.sharedElementEnterTransition = MaterialContainerTransform()
    beginTransaction()
        .addSharedElement(shareElement, "shared_element_container")
        .add(id, fragment, "PlayDetailFragment")
        .addToBackStack("PlayDetailFragment")
        .commit()
}