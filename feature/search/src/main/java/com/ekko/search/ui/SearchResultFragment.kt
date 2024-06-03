package com.ekko.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ekko.repository.model.Card
import com.ekko.repository.model.Nav
import com.ekko.repository.model.SearchResult
import com.ekko.search.databinding.FragmentSearchResultBinding
import com.ekko.search.viewmodel.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private val model: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = arguments?.getString(QUERY, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                initPage(model.getSearchResult(query))
            }
        }
    }

    private fun initPage(result: SearchResult) {
        val navs = result.item_list.map { it.nav }
        val items = result.item_list.map { it.card_list }
        binding.vp2.adapter = VpAdapter(this, items, navs)
        binding.vp2.offscreenPageLimit
        TabLayoutMediator(binding.tab, binding.vp2) { tab, pos ->
            tab.text = navs[pos].title
        }.attach()
    }

    companion object {
        const val QUERY = "query"
    }
}

class VpAdapter(
    fragment: Fragment,
    private val items: List<List<Card>>,
    private val navs: List<Nav>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return SearchResultItemFragment().also {
            it.arguments = bundleOf(SearchResultItemFragment.TYPE to navs[position].title)
        }
    }
}