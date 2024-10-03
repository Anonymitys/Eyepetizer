package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.FeedCoverLargeImageBinding
import com.ekko.repository.model.FeedCoverImage
import com.ekko.repository.model.MetroCard


@PagingViewHolder(CardType.FEED_COVER_LARGE_IMAGE)
class FeedCoverLargeImageViewHolder(
    private val binding: FeedCoverLargeImageBinding,
    jump: (View, String) -> Unit
) :
    PageViewHolder<MetroCard<FeedCoverImage>>(binding, null) {

    override fun bind(card: MetroCard<FeedCoverImage>, position: Int) {
        val image = card.metro_data
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                itemView.context.screenWidth.div(image.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(image.cover?.url) {
            crossfade(true)
        }
    }

}