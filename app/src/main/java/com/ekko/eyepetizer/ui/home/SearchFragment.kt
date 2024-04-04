package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ekko.eyepetizer.R
import com.ekko.eyepetizer.databinding.FragmentPageListBinding
import com.ekko.eyepetizer.databinding.FragmentSearchBinding
import com.ekko.eyepetizer.page.PageAdapter
import com.ekko.eyepetizer.page.PageLoadStateAdapter
import com.ekko.eyepetizer.page.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment() : Fragment() {

    private val model: PageViewModel by viewModels()
    private val pageAdapter = PageAdapter() {
        Log.e("huqiang", "jump: $it")
    }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return pageAdapter.convertViewType2SpanSize(position)
                    }
                }
            }
            adapter = pageAdapter.withLoadStateFooter(
                footer = PageLoadStateAdapter(pageAdapter)
            )
        }
        binding.refresh.setOnRefreshListener { pageAdapter.refresh() }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.getPageData(pageParams.first, pageParams.second).collectLatest {
                    pageAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                pageAdapter.loadStateFlow.collectLatest {
                    binding.refresh.isRefreshing = it.mediator?.refresh is LoadState.Loading
                }
            }
        }
    }

    private val pageParams: Pair<String, String>
        get() = Pair("card", "discover_v2")
}