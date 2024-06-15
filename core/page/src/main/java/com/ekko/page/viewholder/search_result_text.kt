package com.ekko.page.viewholder

import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutFeedTextItemBinding
import com.ekko.repository.model.FeedCoverText

@PagingViewHolder(CardType.SEARCH_RESULT_TEXT)
class SearchResultTextViewHolder(
    private val binding: LayoutFeedTextItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<FeedCoverText>(binding, jump) {

    override fun bind(card: FeedCoverText) {
        binding.title.text = card.text
        binding.consumption.text =
            "${card.consumption?.like_count}人点赞｜${card.consumption?.collection_count}人收藏"
    }
}