package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.FeedCoverSmallItemBinding
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.FEED_COVER_SMALL_VIDEO)
class FeedCoverSmallVideoViewHolder(
    private val binding: FeedCoverSmallItemBinding,
    private val jump: (View, String) -> Unit
) :
    PageViewHolder<MetroCard<FeedCoverVideo>>(binding, jump) {

    override fun bind(card: MetroCard<FeedCoverVideo>, position: Int) {
        val video = card.metro_data
        binding.cover.apply {
            layoutParams.width = (itemView.context.screenWidth - 32.dp) / 2
            layoutParams.height =
                layoutParams.width.div(video.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(video.cover?.url) {
            crossfade(true)
        }
        binding.title.text = video.title
        binding.tag.text = video.tags?.joinToString { it.title }
        binding.duration.text = video.duration?.text?.trim()
    }
}