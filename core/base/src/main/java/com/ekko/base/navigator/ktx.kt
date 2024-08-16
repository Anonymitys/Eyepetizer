package com.ekko.base.navigator

import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.ekko.base.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform


private val ROOT_ID = R.id.container

fun Fragment.start(destination: Fragment, shareElementView: View) {
    val manager = parentFragment?.childFragmentManager ?: return
    ViewCompat.setTransitionName(shareElementView, shareElementView.toString())
    destination.sharedElementEnterTransition = MaterialContainerTransform().apply {
        containerColor = MaterialColors.getColor(
            shareElementView,
            com.google.android.material.R.attr.colorSurface
        )
    }
    manager.beginTransaction()
        .addSharedElement(shareElementView, "shared_element_container")
        .add(ROOT_ID, destination)
        .hide(this)
        .addToBackStack(destination.toString())
        .commit()
}