package com.ekko.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                when (loadingType()) {
                    CENTER_LOADING -> {
                        refresh.isRefreshing =
                            it.source.refresh is LoadState.Loading && pageAdapter.itemCount > 0
                    }

                    REFRESH_LOADING -> {
                        refresh.isRefreshing = it.source.refresh is LoadState.Loading
                    }
                }
                errorView.bindLoadingError(it.source.refresh)
            }
        }
    }

    private fun ErrorView.bindLoadingError(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                if (loadingType() == CENTER_LOADING) {
                    setLoading()
                } else {
                    setComplete()
                }
            }

            is LoadState.Error -> bindErrorMsg(loadState.error.message.toString()) {
                pageAdapter.refresh()
            }

            else -> setComplete()
        }
    }

    /**
     * first:pageType
     * second:pageLabel
     */
    abstract val pageParams: Pair<String, String>

    abstract fun layoutManager(): LayoutManager

    abstract fun navigateTo(view: View, url: String)

    open fun loadingType(): Int {
        return CENTER_LOADING
    }

    companion object {
        const val CENTER_LOADING = 1
        const val REFRESH_LOADING = 2
    }
}