package com.ekko.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.base.view.ErrorView
import com.ekko.page.adapter.PageAdapter
import com.ekko.page.adapter.PageLoadStateAdapter
import com.ekko.page.databinding.FragmentPageListBinding
import com.ekko.page.model.ItemCard
import com.ekko.page.viewmodel.PageViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * 通用列表Fragment
 */
abstract class PageListFragment : Fragment() {

    private val model: PageViewModel by viewModels()
    protected val pageAdapter = PageAdapter(::navigateTo)
    protected lateinit var binding: FragmentPageListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        binding.bindState(model.getPageData(pageParams.first, pageParams.second))
    }


    private fun FragmentPageListBinding.bindState(pagingData: Flow<PagingData<ItemCard>>) {
        list.layoutManager = layoutManager()
        list.adapter = pageAdapter.withLoadStateFooter(
            footer = PageLoadStateAdapter(pageAdapter)
        )
        refresh.setOnRefreshListener { pageAdapter.refresh() }

        launchWhenCreated {
            pagingData.collectLatest {
                pageAdapter.submitData(it)
            }
        }

        launchWhenCreated {
            pageAdapter.loadStateFlow.collectLatest {
                refresh.isRefreshing =
                    it.source.refresh is LoadState.Loading && pageAdapter.itemCount > 0
                progress.isVisible =
                    it.source.refresh is LoadState.Loading && pageAdapter.itemCount == 0
                errorView.bindError(it.source.refresh)

            }
        }
    }

    private fun ErrorView.bindError(loadState: LoadState) {
        val errorState = loadState as? LoadState.Error ?: run {
            isVisible = false
            return
        }
        if (pageAdapter.itemCount == 0) {
            isVisible = true
            bindErrorMsg(errorState.error.message.toString()) {
                pageAdapter.refresh()
            }
        }
    }

    /**
     * first:pageType
     * second:pageLabel
     */
    abstract val pageParams: Pair<String, String>

    abstract fun layoutManager(): LayoutManager

    abstract fun navigateTo(view: View, url: String)
}