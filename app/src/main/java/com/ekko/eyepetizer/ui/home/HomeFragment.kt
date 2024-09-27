package com.ekko.eyepetizer.ui.home

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.base.navigator.start
import com.ekko.page.fragment.PageListFragment
import com.ekko.playdetail.constants.ArgumentsKeys
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
        Log.e("huqiang", "navigateTo: $url")
        val regex = "\\d+".toRegex()
        val resourceId = regex.find(url)?.value?.toLong() ?: 0
        parentFragment?.start(PlayDetailFragment().apply {
            arguments = bundleOf(ArgumentsKeys.RESOURCE_ID to resourceId)
        }, view)
    }

}