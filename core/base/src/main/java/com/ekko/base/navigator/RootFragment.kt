package com.ekko.base.navigator

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ekko.base.R

class RootFragment : Fragment(R.layout.fragment_root) {

    fun fill(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    childFragmentManager.popBackStack()
                }
            }
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), onBackPressedCallback)
        childFragmentManager.addOnBackStackChangedListener {
            onBackPressedCallback.isEnabled = childFragmentManager.backStackEntryCount > 0
        }
    }
}