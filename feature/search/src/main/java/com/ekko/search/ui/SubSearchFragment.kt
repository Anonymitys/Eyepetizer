package com.ekko.search.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubSearchFragment(
    override val pageParams: Pair<String, String> = Pair(
        "card",
        "discover_v2"
    )
) : PageListFragment() {

    override fun layoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun navigateTo(view: View, url: String) {

    }
}