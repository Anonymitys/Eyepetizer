package com.ekko.eyepetizer.ui.home

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.base.navigator.start
import com.ekko.page.fragment.PageListFragment
import com.ekko.playdetail.ui.PlayDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "recommend")

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }


    override fun navigateTo(view: View, url: String) {
        parentFragment?.start(PlayDetailFragment(), view)
    }

}