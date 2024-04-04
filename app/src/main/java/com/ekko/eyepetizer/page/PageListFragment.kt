package com.ekko.eyepetizer.page

import android.content.Context
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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.eyepetizer.MainActivity
import com.ekko.eyepetizer.R
import com.ekko.eyepetizer.databinding.FragmentPageListBinding
import com.ekko.eyepetizer.page.viewholder.ViewType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * 通用列表Fragment
 */
abstract class PageListFragment : Fragment() {

    private val model: PageViewModel by viewModels()
    private val pageAdapter = PageAdapter() {
        Log.e("huqiang", "jump: $it")
    }
    protected lateinit var binding: FragmentPageListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2).also {
                it.spanSizeLookup = object : SpanSizeLookup() {
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
                pageAdapter.loadStateFlow
                    .collectLatest {
                        binding.refresh.isRefreshing = it.mediator?.refresh is LoadState.Loading
                    }
            }
        }
    }

    /**
     * first:pageType
     * second:pageLabel
     */
    abstract val pageParams: Pair<String, String>
}