package com.ekko.uploader.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.ekko.base.viewpager.VpAdapter
import com.ekko.repository.model.UserInfo
import com.ekko.uploader.di.VideoPageScope
import com.ekko.uploader.model.IntentParameter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.therouter.TheRouter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@VideoPageScope
class ContentService @Inject constructor(
    private val data: UserInfo,
    private val scope: CoroutineScope,
    private val containerTree: ContainerTree,
    private val activity: FragmentActivity,
    private val intentParameter: IntentParameter,
) {

    private val vp2: ViewPager2
        get() = containerTree.binding.vp2

    private val tabs: TabLayout
        get() = containerTree.binding.tabLayout

    init {
        val navList = data.nav_tabs.nav_list
        navList.mapNotNull {
            TheRouter.build(it.url).createFragment<Fragment>()
        }.takeIf { it.isNotEmpty() }?.let {
            val adapter = VpAdapter(it, activity)
            vp2.adapter = adapter
            tabs.tabMode = TabLayout.MODE_SCROLLABLE
            TabLayoutMediator(tabs, vp2) { tab, position ->
                tab.text = navList[position].title
            }.attach()
            // vp2.currentItem = intentParameter.tabIndex
        }
    }
}