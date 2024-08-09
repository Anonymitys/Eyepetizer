package com.ekko.search.ui

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
import androidx.lifecycle.whenResumed
import androidx.lifecycle.withResumed
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekko.page.adapter.PageAdapter
import com.ekko.page.adapter.PageLoadStateAdapter
import com.ekko.search.databinding.FragmentSearchResultBinding
import com.ekko.search.databinding.FragmentSearchResultItemBinding
import com.ekko.search.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultItemFragment : Fragment() {
    private val model: SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var binding: FragmentSearchResultItemBinding
    private lateinit var type: String
    private val adapter = PageAdapter { view, url ->
        Log.e("huqiang", "jump: $url")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(TYPE, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        lifecycleScope.launch {
            withResumed { }
            binding.list.layoutManager = LinearLayoutManager(context)
            binding.list.adapter = adapter.withLoadStateFooter(PageLoadStateAdapter(adapter))
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.getSearchItemResult(type).collectLatest {
                    adapter.submitData(it)
                }

            }
        }
    }

    companion object {
        const val TYPE = "type"
    }
}