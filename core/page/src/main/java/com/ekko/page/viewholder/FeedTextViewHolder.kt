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
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val data = json.decodeFromJsonElement<FeedCoverText>(card.data[0].metro_data)
        binding.title.text = data.text
        binding.consumption.text =
            "${data.consumption?.like_count}人点赞｜${data.consumption?.collection_count}人收藏"
        binding.root.setOnClickListener {
            jump(card.data[0].link)
        }
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