package com.ekko.page.viewholder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekko.base.ktx.dp
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.adapter.TopicsAdapter
import com.ekko.page.databinding.LayoutTopicsListBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.TopicsPlayList

@PagingViewHolder(CardType.SLIDE_COVER_IMAGE)
class SlideCoverImageViewHolder(
    private val binding: LayoutTopicsListBinding,
    private val jump: (View, String) -> Unit
) : SlideViewHolder<MetroCard<TopicsPlayList>>(binding, jump) {

    override fun bind(card: List<MetroCard<TopicsPlayList>>, position: Int) {
        val adapter = TopicsAdapter(card, jump)
        binding.list.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        if (binding.list.itemDecorationCount <= 0) {
            binding.list.addItemDecoration(LineSpaceItemDecoration(8.dp))
        }
        binding.list.adapter = adapter
    }
}