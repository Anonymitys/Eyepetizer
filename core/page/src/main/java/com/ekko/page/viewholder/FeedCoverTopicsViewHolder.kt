package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import coil.load
import com.ekko.base.json
import com.google.android.material.R
import com.ekko.page.databinding.LayoutFeedCoverTopicItemBinding
import com.ekko.page.databinding.LayoutFeedUserBinding
import com.ekko.page.databinding.LayoutGraphicItemBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.FeedCoverTopic
import kotlinx.serialization.json.decodeFromJsonElement

class FeedCoverTopicsViewHolder(
    private val binding: LayoutFeedCoverTopicItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val data = json.decodeFromJsonElement<FeedCoverTopic>(card.data[0].metro_data)
        binding.cover.load(data.cover?.url)
        binding.title.text = data.title
        binding.desc.text = data.description
        binding.tags.removeAllViews()
        data.tags?.forEach { tag ->
            val view =
                TextView(itemView.context, null, R.attr.textAppearanceLabelSmall).also {
                    it.text = tag.title
                }
            binding.tags.addView(view)
        }
        binding.root.setOnClickListener {
            jump(card.data[0].link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FeedCoverTopicsViewHolder {
            val binding = LayoutFeedCoverTopicItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return FeedCoverTopicsViewHolder(binding, jump)
        }
    }
}