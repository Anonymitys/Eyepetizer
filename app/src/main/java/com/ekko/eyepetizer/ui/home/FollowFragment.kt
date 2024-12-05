package com.ekko.eyepetizer.ui.home

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.base.navigator.navigateTo
import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FollowFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "follow")

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }


    override fun navigateTo(view: View, url: String) {
        Log.e("huqiang", "navigateTo: $url")
        navigateTo(url)
    }

}