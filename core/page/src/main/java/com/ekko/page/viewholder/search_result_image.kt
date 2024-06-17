package com.ekko.page.viewholder

import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutGraphicItemBinding
import com.ekko.repository.model.GraphicCard

@PagingViewHolder(CardType.SEARCH_RESULT_IMAGE)
class SearchResultImageViewHolder(
    private val binding: LayoutGraphicItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<GraphicCard>(binding, jump) {

    override fun bind(
        card: GraphicCard,
        position: Int
    ) {
        binding.cover.load(card.cover?.url)
        binding.title.text = card.title
        binding.consumption.text =
            "${card.consumption?.like_count}人点赞｜${card.consumption?.collection_count}人收藏"
    }
}