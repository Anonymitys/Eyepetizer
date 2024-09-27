package com.ekko.playdetail.service

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.ekko.playdetail.adapter.VpAdapter
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.constants.Type
import com.ekko.playdetail.ui.ContentFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class TabService @Inject constructor(
    containerViewTree: ContainerViewTree,
    fragment: Fragment
) {
    private val tabLayout = containerViewTree.binding.tabLayout
    private val vp2 = containerViewTree.binding.vp2

    init {
        vp2.adapter =
            VpAdapter(listOf(ContentFragment().apply {
                arguments = bundleOf(ArgumentsKeys.TAB_TYPE to Type.INTRO)
            }, ContentFragment().apply {
                arguments = bundleOf(ArgumentsKeys.TAB_TYPE to Type.COMMENT)
            }), fragment)
        TabLayoutMediator(tabLayout, vp2) { tab, position ->
            when (position) {
                0 -> tab.text = "简介"
                1 -> tab.text = "评论"
            }
        }.attach()
    }
}