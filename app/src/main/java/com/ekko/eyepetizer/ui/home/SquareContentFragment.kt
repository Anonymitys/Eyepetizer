package com.ekko.eyepetizer.ui.home

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.base.navigator.start
import com.ekko.page.fragment.PageListFragment
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.ui.PlayDetailFragment
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
        val regex = "\\d+".toRegex()
        val resourceId = regex.find(url)?.value?.toLong() ?: 0
        val playUrl = Uri.parse(url).getQueryParameter("play_url") ?: ""
        parentFragment?.start(PlayDetailFragment().apply {
            arguments =
                bundleOf(ArgumentsKeys.RESOURCE_ID to resourceId, ArgumentsKeys.PLAY_URL to playUrl)
        }, view)
    }
}