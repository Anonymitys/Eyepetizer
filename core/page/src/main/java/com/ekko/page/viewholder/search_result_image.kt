package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutGraphicItemBinding
import com.ekko.repository.model.GraphicCard
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.SEARCH_RESULT_IMAGE)
class SearchResultImageViewHolder(
    private val binding: LayoutGraphicItemBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<GraphicCard>>(binding, jump) {

    override fun bind(card: MetroCard<GraphicCard>, position: Int) {
        val data = card.metro_data
        binding.cover.load(data.cover?.url)
        binding.title.text = data.title
        binding.consumption.text =
            "${data.consumption?.like_count}人点赞｜${data.consumption?.collection_count}人收藏"
    }
}