package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.eyepetizer.databinding.FragmentSearchBinding
import com.ekko.eyepetizer.page.PageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment:PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "discover_v2")
}