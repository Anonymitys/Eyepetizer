package com.ekko.eyepetizer.ui.home

import com.ekko.page.fragment.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubSearchFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "discover_v2")
}