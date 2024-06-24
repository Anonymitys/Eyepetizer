package com.ekko.page.viewholder

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.R
import com.ekko.page.databinding.FeedItemDetailItemBinding
import com.ekko.repository.model.FeedItemDetailCard
import com.google.android.material.chip.Chip

@PagingViewHolder(CardType.FEED_ITEM_DETAIL)
class FeedItemDetailViewHolder(
    private val binding: FeedItemDetailItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedItemDetailCard>(binding, jump) {

    override fun bind(
        card: FeedItemDetailCard,
        position: Int
    ) {
        binding.avatar.load(card.author?.avatar?.url)
        binding.nickName.text = card.author?.nick
        binding.publishTime.text = card.publish_time
        binding.collect.isVisible = card.show_follow_btn
        binding.cover.apply {
            layoutParams.width = context.screenWidth
            layoutParams.height =
                layoutParams.width.div(card.video?.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.video?.cover?.url) {
            crossfade(true)
        }

        binding.desc.text = card.text
        binding.tag.removeAllViews()
        card.topics?.forEach {
            val assist = Chip(
                itemView.context
            )
            assist.text = it.title
            assist.chipIcon = ContextCompat.getDrawable(itemView.context, R.drawable.ic_tag_16px)
            binding.tag.addView(assist)
        }

        binding.like.text = card.consumption?.like_count.toString()
        binding.collected.text = card.consumption?.collection_count.toString()
        binding.comment.text = card.consumption?.comment_count.toString()
    }
}