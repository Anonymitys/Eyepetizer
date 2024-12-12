package com.ekko.page.viewholder

import android.view.View
import coil.load
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutCardUserItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.Uploader


@PagingViewHolder(CardType.CARD_USER)
class CardUserViewHolder(
    private val binding: LayoutCardUserItemBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<Uploader>>(binding, jump) {
    override fun bind(card: MetroCard<Uploader>, position: Int) {
        val data = card.metro_data
        binding.cover.load(data.avatar?.url)
        binding.nickName.text = data.nick
        binding.desc.text = data.description
        binding.root.setOnClickListener {
            jump(it, card.link)
        }
    }

}