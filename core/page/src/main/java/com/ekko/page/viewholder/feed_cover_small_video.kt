package com.ekko.page.viewholder

import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.FeedCoverSmallItemBinding
import com.ekko.repository.model.FeedCoverVideo

@PagingViewHolder(CardType.FEED_COVER_SMALL_VIDEO)
class FeedCoverSmallVideoViewHolder(
    private val binding: FeedCoverSmallItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedCoverVideo>(binding, jump) {

    override fun bind(
        card: FeedCoverVideo,
        position: Int
    ) {
        binding.cover.apply {
            layoutParams.width = (itemView.context.screenWidth - 32.dp) / 2
            layoutParams.height = layoutParams.width.div(card.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.cover?.url) {
            crossfade(true)
        }
        binding.title.text = card.title
        binding.tag.text = card.tags?.joinToString { it.title }
        binding.duration.text = card.duration?.text?.trim()
    }
}