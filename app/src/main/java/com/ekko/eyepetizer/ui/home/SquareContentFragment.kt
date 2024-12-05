package com.ekko.eyepetizer.ui.home

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.base.navigator.navigateTo
import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareContentFragment : PageListFragment() {

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

    override fun navigateTo(view: View, url: String) {
        Log.e("huqiang", "navigateTo: $url")
        navigateTo(url)
    }
}