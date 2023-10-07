package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ekko.eyepetizer.databinding.FragmentHomeBinding
import com.ekko.eyepetizer.page.PageListFragment
import com.ekko.eyepetizer.page.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : PageListFragment() {

    override val pageParams: Pair<String, String>
        get() = Pair("card", "recommend")
}