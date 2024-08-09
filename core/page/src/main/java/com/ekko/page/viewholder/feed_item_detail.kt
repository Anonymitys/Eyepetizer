package com.ekko.page.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.R
import com.ekko.page.databinding.FeedItemDetailItemBinding
import com.ekko.repository.model.FeedItemDetailCard
import com.ekko.repository.model.MetroCard
import com.google.android.material.chip.Chip

@PagingViewHolder(CardType.FEED_ITEM_DETAIL)
class FeedItemDetailViewHolder(
    private val binding: FeedItemDetailItemBinding,
    private val jump: (View,String) -> Unit
) :
    PageViewHolder<MetroCard<FeedItemDetailCard>>(binding, jump) {

    override fun bind(card: MetroCard<FeedItemDetailCard>, position: Int) {
        val itemCard = card.metro_data
        binding.avatar.load(itemCard.author?.avatar?.url)
        binding.nickName.text = itemCard.author?.nick
        binding.publishTime.text = itemCard.publish_time
        binding.collect.isVisible = itemCard.show_follow_btn
        binding.cover.apply {
            layoutParams.width = context.screenWidth
            layoutParams.height =
                layoutParams.width.div(itemCard.video?.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(itemCard.video?.cover?.url) {
            crossfade(true)
        }

        binding.desc.text = itemCard.text
        binding.tag.removeAllViews()
        itemCard.topics?.forEach {
            val assist = Chip(
                itemView.context
            )
            assist.text = it.title
            assist.chipIcon = ContextCompat.getDrawable(itemView.context, R.drawable.ic_tag_16px)
            binding.tag.addView(assist)
        }

        binding.like.text = itemCard.consumption?.like_count.toString()
        binding.collected.text = itemCard.consumption?.collection_count.toString()
        binding.comment.text = itemCard.consumption?.comment_count.toString()
    }
}