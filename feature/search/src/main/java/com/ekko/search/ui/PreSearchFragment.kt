package com.ekko.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekko.base.ktx.hide
import com.ekko.search.adapter.RecommendKeyAdapter
import com.ekko.search.databinding.FragmentPreSearchBinding
import com.ekko.search.viewmodel.QuerySearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PreSearchFragment : Fragment() {
    private val querySearchViewModel: QuerySearchViewModel by activityViewModels()
    private lateinit var binding: FragmentPreSearchBinding
    private lateinit var keys: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keys = arguments?.getStringArrayList(KEY) ?: ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val adapter = RecommendKeyAdapter(keys) { key ->
            querySearchViewModel.submitSearchQuery(key)
            parentFragmentManager.hide(this)
        }
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    companion object {
        private const val TAG = "PreSearchFragment"
        const val KEY = "key"
    }
}