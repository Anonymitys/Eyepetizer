package com.ekko.search.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekko.base.ktx.json
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.base.navigator.navigateTo
import com.ekko.repository.model.CardHeader
import com.ekko.search.adapter.HeaderAdapter
import com.ekko.search.adapter.HotKeysAdapter
import com.ekko.search.adapter.RecommendVideoAdapter
import com.ekko.search.databinding.FragmentSearchRecommendListBinding
import com.ekko.search.viewmodel.QuerySearchViewModel
import com.ekko.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.decodeFromJsonElement

@AndroidEntryPoint
class SearchRecommendFragment : Fragment() {

    private val model: SearchViewModel by viewModels()
    private val querySearchViewModel: QuerySearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchRecommendListBinding
    private val adapter: ConcatAdapter = ConcatAdapter()

    private val jump: (View, String) -> Unit = { itemView, url ->
        Log.e("huqiang", "navigateTo: $url")
        navigateTo(url)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchRecommendListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        launchWhenCreated {
            val list = model.getHotQueries()
            if (list.isNotEmpty()) {
                adapter.addAdapter(0, HeaderAdapter(CardHeader("推荐搜索")))
                adapter.addAdapter(1, HotKeysAdapter(list) {
                    querySearchViewModel.submitSearchQuery(it)
                })
            }
        }
        launchWhenCreated {
            model.getRecommendList().firstOrNull()?.apply {
                card_data?.header?.left?.firstOrNull()?.let {
                    adapter.addAdapter(HeaderAdapter(json.decodeFromJsonElement(it.metro_data)))
                }
                card_data?.body?.metro_list?.let {
                    adapter.addAdapter(RecommendVideoAdapter(it, jump))
                }
            }
        }
    }
}