package com.ekko.page.viewholder

import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.FeedCoverLargeItemBinding
import com.ekko.repository.model.FeedCoverVideo

@PagingViewHolder(CardType.FEED_COVER_LARGE_VIDEO)
class FeedCoverLargeVideoViewHolder(
    private val binding: FeedCoverLargeItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedCoverVideo>(binding, jump) {
    override fun bind(card: FeedCoverVideo) {
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                itemView.context.screenWidth.div(card.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.cover?.url) {

        }
        binding.avatar.load(card.author?.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = card.title
        binding.nickName.text = card.author?.nick
        binding.tag.text = card.tags?.joinToString { it.title }
        binding.duration.text = card.duration?.text
    }
}