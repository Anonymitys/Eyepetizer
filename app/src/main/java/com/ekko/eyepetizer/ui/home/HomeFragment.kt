package com.ekko.eyepetizer.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "recommend")

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }
}