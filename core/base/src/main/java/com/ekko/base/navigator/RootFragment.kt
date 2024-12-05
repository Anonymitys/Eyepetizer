package com.ekko.base.navigator

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ekko.base.R

class RootFragment : Fragment(R.layout.fragment_root) {

    fun fill(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(ROOT_ID, fragment)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                    childFragmentManager.popBackStack()
                }
            }
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), onBackPressedCallback)
        childFragmentManager.addOnBackStackChangedListener {
            onBackPressedCallback.isEnabled = childFragmentManager.backStackEntryCount > 0
        }
    }

    fun navigation(destination: Fragment) {
        childFragmentManager.beginTransaction()
            // .addSharedElement(shareElementView, "shared_element_container")
            .add(ROOT_ID, destination)
            .addToBackStack(destination.toString())
            .commit()
    }

    companion object {
        private val ROOT_ID = R.id.container
    }


}