package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.FeedCoverLargeItemBinding
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.FEED_COVER_LARGE_VIDEO)
class FeedCoverLargeVideoViewHolder(
    private val binding: FeedCoverLargeItemBinding,
    private val jump: (View, String) -> Unit
) :
    PageViewHolder<MetroCard<FeedCoverVideo>>(binding, jump) {
    override fun bind(card: MetroCard<FeedCoverVideo>, position: Int) {
        val video = card.metro_data
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                itemView.context.screenWidth.div(video.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(video.cover?.url) {

        }
        binding.avatar.load(video.author?.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = video.title
        binding.nickName.text = video.author?.nick
        binding.tag.text = video.tags?.joinToString { it.title }
        binding.duration.text = video.duration?.text
        binding.avatar.setOnClickListener {
            jump(it, video.author?.link ?: "")
        }
    }
}