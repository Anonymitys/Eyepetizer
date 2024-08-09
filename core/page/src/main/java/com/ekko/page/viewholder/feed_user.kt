package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutFeedUserBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.Uploader

@PagingViewHolder(CardType.FEED_USER)
class FeedUserViewHolder(
    private val binding: LayoutFeedUserBinding,
    private val jump: (View,String) -> Unit
) : PageViewHolder<MetroCard<Uploader>>(binding, jump) {

    override fun bind(card: MetroCard<Uploader>, position: Int) {
        val data = card.metro_data
        binding.avatar.load(data.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = data.nick
        binding.desc.text = data.description
    }
}