package com.ekko.page.viewholder

import android.view.View
import android.widget.TextView
import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.google.android.material.R
import com.ekko.page.databinding.LayoutFeedCoverTopicItemBinding
import com.ekko.repository.model.FeedCoverTopic
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.FEED_COVER_DETAIL_TOPIC)
class FeedCoverDetailTopicViewHolder(
    private val binding: LayoutFeedCoverTopicItemBinding,
    private val jump: (View,String) -> Unit
) : PageViewHolder<MetroCard<FeedCoverTopic>>(binding, jump) {

    override fun bind(card: MetroCard<FeedCoverTopic>, position: Int) {
        val topic = card.metro_data
        binding.cover.load(topic.cover?.url)
        binding.title.text = topic.title
        binding.desc.text = topic.description
        binding.tags.removeAllViews()
        topic.tags?.forEach { tag ->
            val view =
                TextView(itemView.context, null, R.attr.textAppearanceLabelSmall).also {
                    it.text = tag.title
                }
            binding.tags.addView(view)
        }
    }
}