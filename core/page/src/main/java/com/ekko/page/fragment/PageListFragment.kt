package com.ekko.page.fragment

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
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ekko.page.adapter.PageAdapter
import com.ekko.page.adapter.PageLoadStateAdapter
import com.ekko.page.databinding.FragmentPageListBinding
import com.ekko.page.viewmodel.PageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 通用列表Fragment
 */
abstract class PageListFragment : Fragment() {

    private val model: PageViewModel by viewModels()
    protected val pageAdapter = PageAdapter() {
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
            layoutManager = layoutManager()
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

    abstract fun layoutManager(): LayoutManager
}