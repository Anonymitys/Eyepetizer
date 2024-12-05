package com.ekko.base.navigator

import android.view.View
import androidx.fragment.app.Fragment
import com.ekko.base.R
import com.therouter.TheRouter


val ROOT_ID = R.id.container
val ROOT_TAG = "RootFragment::Tag"

fun Fragment.start(destination: Fragment, shareElementView: View) {
    val manager = parentFragment?.childFragmentManager ?: return
//    ViewCompat.setTransitionName(shareElementView, shareElementView.toString())
//    destination.sharedElementEnterTransition = MaterialContainerTransform().apply {
//        containerColor = MaterialColors.getColor(
//            shareElementView,
//            com.google.android.material.R.attr.colorSurface
//        )
//    }
    manager.beginTransaction()
        // .addSharedElement(shareElementView, "shared_element_container")
        .add(ROOT_ID, destination)
        .hide(this)
        .addToBackStack(destination.toString())
        .commit()
}


fun Fragment.navigateTo(url: String) {
    val fragment = TheRouter.build(url).createFragment<Fragment>() ?: return
    val rootFragment =
        activity?.supportFragmentManager?.findFragmentByTag(ROOT_TAG) as? RootFragment ?: return
    rootFragment.navigation(fragment)
}