package com.ekko.eyepetizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.eyepetizer.databinding.FragmentMainBinding
import com.ekko.eyepetizer.ui.home.HomeFragment
import com.ekko.eyepetizer.ui.home.MineFragment
import com.ekko.eyepetizer.ui.home.SquareFragment
import com.ekko.search.ui.SearchFragment
import com.google.android.material.transition.Hold

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val fragments = mapOf(
        R.id.navigation_home.toString() to HomeFragment(),
        R.id.navigation_square.toString() to SquareFragment(),
        R.id.navigation_search.toString() to SearchFragment(),
        R.id.navigation_mine.toString() to MineFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fill(R.id.navigation_home.toString())
        binding.navView.setOnItemSelectedListener {
            fill(it.itemId.toString())
            true
        }
        binding.navView.setOnItemReselectedListener {
            //
        }
    }

    private fun fill(tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        val fragment = childFragmentManager.findFragmentByTag(tag)
        if (fragment?.isAdded == true) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.container, fragments[tag]!!, tag)
        }
        childFragmentManager.fragments.find {
            fragments.keys.contains(it.tag) && it.isVisible
        }?.let { transaction.hide(it) }
        transaction.commit()
    }
}