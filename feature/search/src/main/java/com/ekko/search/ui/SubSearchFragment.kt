package com.ekko.search.ui

import com.ekko.page.fragment.ScrollListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubSearchFragment(
    override val pageParams: Pair<String, String> = Pair(
        "card",
        "discover_v2"
    )
) : ScrollListFragment()