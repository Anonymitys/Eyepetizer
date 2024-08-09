package com.ekko.page.viewholder

import android.view.View
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutFeedTextItemBinding
import com.ekko.repository.model.FeedCoverText
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.SEARCH_RESULT_TEXT)
class SearchResultTextViewHolder(
    private val binding: LayoutFeedTextItemBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<FeedCoverText>>(binding, jump) {

    override fun bind(card: MetroCard<FeedCoverText>, position: Int) {
        val data = card.metro_data
        binding.title.text = data.text
        binding.consumption.text =
            "${data.consumption?.like_count}人点赞｜${data.consumption?.collection_count}人收藏"
    }
}