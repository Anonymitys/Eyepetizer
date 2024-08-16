package com.ekko.playdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.withResumed
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.play.detail.databinding.FragmentPlayContentBinding
import com.ekko.playdetail.adapter.ContentAdapter
import com.ekko.playdetail.adapter.PlayItemCard
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContentFragment : Fragment() {

    private lateinit var binding: FragmentPlayContentBinding
    private val model: PlayDetailViewModel by viewModels()

    private var content = mutableListOf<PlayItemCard>()
    private val adapter = ContentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayContentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.adapter = adapter
        launchWhenCreated {
            withResumed {  }
            val data = model.playDetail("324933", "pgc_video")
            content = buildList {
                add(PlayItemCard("content", data))
                addAll(content)
            }.toMutableList()
            adapter.submitList(content)

        }

        launchWhenCreated {
            withResumed {  }
            val recommend = model.relateRecommend("324933", "pgc_video")
            content = buildList {
                addAll(content)
                addAll(recommend.item_list?.map { PlayItemCard("recommend", it) } ?: emptyList())
            }.toMutableList()
            adapter.submitList(content)
        }
    }
}