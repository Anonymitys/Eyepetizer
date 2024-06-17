package com.ekko.page.viewholder

import android.widget.TextView
import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.google.android.material.R
import com.ekko.page.databinding.LayoutFeedCoverTopicItemBinding
import com.ekko.repository.model.FeedCoverTopic

@PagingViewHolder(CardType.FEED_COVER_DETAIL_TOPIC)
class FeedCoverDetailTopicViewHolder(
    private val binding: LayoutFeedCoverTopicItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<FeedCoverTopic>(binding, jump) {

    override fun bind(
        card: FeedCoverTopic,
        position: Int
    ) {
        binding.cover.load(card.cover?.url)
        binding.title.text = card.title
        binding.desc.text = card.description
        binding.tags.removeAllViews()
        card.tags?.forEach { tag ->
            val view =
                TextView(itemView.context, null, R.attr.textAppearanceLabelSmall).also {
                    it.text = tag.title
                }
            binding.tags.addView(view)
        }
    }
}