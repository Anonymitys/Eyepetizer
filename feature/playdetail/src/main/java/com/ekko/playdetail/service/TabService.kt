package com.ekko.playdetail.service

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import com.ekko.base.viewpager.VpAdapter
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.constants.Type
import com.ekko.playdetail.ui.ContentFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class TabService @Inject constructor(
    containerViewTree: ContainerViewTree,
    activity: FragmentActivity
) {
    private val tabLayout = containerViewTree.binding.tabLayout
    private val vp2 = containerViewTree.binding.vp2

    init {
        vp2.adapter =
            VpAdapter(listOf(ContentFragment().apply {
                arguments = bundleOf(ArgumentsKeys.TAB_TYPE to Type.INTRO)
            }, ContentFragment().apply {
                arguments = bundleOf(ArgumentsKeys.TAB_TYPE to Type.COMMENT)
            }), activity)
        TabLayoutMediator(tabLayout, vp2) { tab, position ->
            when (position) {
                0 -> tab.text = "简介"
                1 -> tab.text = "评论"
            }
        }.attach()
    }
}