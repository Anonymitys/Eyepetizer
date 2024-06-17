package com.ekko.page.viewholder

import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutFeedUserBinding
import com.ekko.repository.model.Uploader

@PagingViewHolder(CardType.FEED_USER)
class FeedUserViewHolder(
    private val binding: LayoutFeedUserBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<Uploader>(binding, jump) {

    override fun bind(
        card: Uploader,
        position: Int
    ) {
        binding.avatar.load(card.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = card.nick
        binding.desc.text = card.description
    }
}