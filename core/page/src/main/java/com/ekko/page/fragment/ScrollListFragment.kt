package com.ekko.page.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.page.databinding.FragmentScrollListBinding
import com.ekko.page.model.FooterItemCard
import com.ekko.page.model.HeaderItemCard
import com.ekko.page.model.MetroItemCard
import com.ekko.page.model.SlideItemCard
import com.ekko.page.viewholder.Converter
import com.ekko.page.viewholder.DefaultViewHolder
import com.ekko.page.viewholder.ViewHolderFactory
import com.ekko.page.viewmodel.ScrollListViewModel
import com.ekko.page.viewmodel.UIState
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.toMetroCard
import kotlinx.coroutines.flow.collectLatest

abstract class ScrollListFragment : Fragment() {

    private lateinit var binding: FragmentScrollListBinding
    private val model by viewModels<ScrollListViewModel>()
    private val jump: (View, String) -> Unit = { itemView, url ->

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        load()
        binding.refresh.setOnRefreshListener { load() }
    }

    private fun FragmentScrollListBinding.bindState(uiState: UIState) {
        progress.isVisible = uiState is UIState.Loading && list.childCount <= 0
        if (uiState !is UIState.Loading) {
            refresh.isRefreshing = false
            list.removeAllViews()
        }
        when (uiState) {
            is UIState.Success -> {
                uiState.data.forEach { card ->
                    val viewType = Converter.convertCardType2ViewType(card.type)
                    val holder = ViewHolderFactory.create(
                        viewType, binding.list, jump
                    )

                    if (holder is DefaultViewHolder) return@forEach
                    when (card) {
                        is MetroItemCard -> {
                            val type =
                                holder::class.supertypes[0].arguments[0].type?.arguments?.get(0)?.type
                                    ?: return
                            val metroCard = card.data.toMetroCard(type)
                            holder.bind(metroCard, card.index)
                            holder.itemView.setOnClickListener {
                                val playUrl =
                                    (metroCard.metro_data as? FeedCoverVideo)?.play_url ?: ""
                                val uri = Uri.parse(card.data.link).buildUpon()
                                    .appendQueryParameter("play_url", playUrl).build()
                                jump(holder.itemView, uri.toString())
                            }
                        }

                        is SlideItemCard -> {
                            val type =
                                holder::class.supertypes[0].arguments[0].type?.arguments?.get(0)?.type
                                    ?: return
                            holder.bind(card.data.map {
                                it.toMetroCard(type)
                            }, 0)
                        }

                        is FooterItemCard -> {
                            holder.bind("", 0)
                        }

                        is HeaderItemCard -> {
                            holder.bind(card.left, 0)
                        }

                    }
                    list.addView(holder.itemView)
                }
            }

            is UIState.Empty -> {

            }

            is UIState.Error -> {
                errorView.bindErrorMsg(uiState.message) {

                }
            }

            is UIState.Loading -> {

            }
        }
    }

    private fun load() {
        launchWhenCreated {
            model.page(
                "card", "discover_v2"
            ).collectLatest {
                binding.bindState(it)

            }
        }
    }

    abstract val pageParams: Pair<String, String>
}