package com.ekko.eyepetizer.ui.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "community")

    override fun layoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return pageAdapter.convertViewType2SpanSize(position, 2)
                }
            }
        }
    }
}