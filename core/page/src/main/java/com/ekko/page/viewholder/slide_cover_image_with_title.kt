package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.dp
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.adapter.TopicsAdapter
import com.ekko.page.databinding.LayoutTopicsItemBinding
import com.ekko.page.databinding.LayoutTopicsListBinding
import com.ekko.repository.model.TopicsPlayList

@PagingViewHolder(CardType.SLIDE_COVER_IMAGE_WITH_TITLE)
class SlideCoverImageWithTitleViewHolder(
    private val binding: LayoutTopicsListBinding,
    private val jump: (String) -> Unit
) : SlideViewHolder<TopicsPlayList>(binding, jump) {

    override fun bind(card: List<TopicsPlayList>) {
        val adapter = TopicsAdapter(card, jump)
        binding.list.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        if (binding.list.itemDecorationCount <= 0) {
            binding.list.addItemDecoration(LineSpaceItemDecoration(16.dp))
        }
        binding.list.adapter = adapter
    }
}