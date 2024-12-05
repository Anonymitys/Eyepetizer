package com.ekko.search.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ekko.base.ktx.hide
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.base.ktx.showFragment
import com.ekko.search.R
import com.ekko.search.databinding.FragmentSearchBinding
import com.ekko.search.viewmodel.QuerySearchViewModel
import com.ekko.search.viewmodel.SearchViewModel
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment() : Fragment() {

    private val searchModel: SearchViewModel by viewModels()
    private val querySearchViewModel: QuerySearchViewModel by activityViewModels()
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
        setUpSearchView(binding.catSearchBar, binding.catSearchView)
        loadPage()
        preQuerySearch()
        querySearch()
    }

    private fun setUpSearchView(
        searchBar: SearchBar,
        searchView: SearchView
    ) {
        launchWhenCreated {
            val hint = searchModel.defaultWord().takeIf { it.isNotEmpty() }
                ?: getString(R.string.cat_searchbar_hint)
            searchBar.hint = hint
            searchView.hint = hint
        }

        searchView
            .editText
            .setOnEditorActionListener { _, _, event: KeyEvent? ->
                if (event?.keyCode == KEYCODE_ENTER) {
                    searchView.clearFocusAndHideKeyboard()
                    querySearchViewModel.submitSearchQuery(searchView.text.toString())
                }
                false
            }
        searchView.editText.doAfterTextChanged {
            querySearchViewModel.submitPreSearchQuery(it.toString())
        }
        searchView.addTransitionListener { _, _, newState: SearchView.TransitionState ->
            if (newState == SearchView.TransitionState.SHOWN) {
                childFragmentManager.showFragment<SearchRecommendFragment>(
                    R.id.search_view_container
                )
            } else {
                childFragmentManager.hide(R.id.search_view_container)
                childFragmentManager.hide(R.id.search_view_result_container)
            }
        }
    }

    private fun loadPage() {
        childFragmentManager.beginTransaction().replace(R.id.search_container, SubSearchFragment())
            .commitAllowingStateLoss()
    }

    private fun preQuerySearch() {
        launchWhenCreated {
            querySearchViewModel.preSearchQuery(debounce = 20).collectLatest {
                if (it.isEmpty()) {
                    childFragmentManager.hide(R.id.search_view_result_container)
                } else if (binding.catSearchView.editText.isFocused) {
                    val list = searchModel.getRecommendSearchKey(it)
                    childFragmentManager.showFragment<PreSearchFragment>(
                        R.id.search_view_result_container,
                        args = bundleOf(PreSearchFragment.KEY to list)
                    )
                }
            }
        }
    }

    private fun querySearch() {
        launchWhenCreated {
            querySearchViewModel.searchQuery().collectLatest {
                binding.catSearchView.editText.setText(it)
                childFragmentManager.showFragment<SearchResultFragment>(
                    R.id.search_view_result_container,
                    args = bundleOf(SearchResultFragment.QUERY to it)
                )
            }
        }
    }
}