package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ekko.base.json
import com.ekko.page.databinding.LayoutFeedTextItemBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.FeedCoverText
import kotlinx.serialization.json.decodeFromJsonElement

class FeedTextViewHolder(
    private val binding: LayoutFeedTextItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<FeedCoverText>(binding, jump) {

    override fun bind(card: FeedCoverText) {
        binding.title.text = card.text
        binding.consumption.text =
            "${card.consumption?.like_count}人点赞｜${card.consumption?.collection_count}人收藏"
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FeedTextViewHolder {
            val binding = LayoutFeedTextItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return FeedTextViewHolder(binding, jump)
        }
    }
}