package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.json
import com.ekko.page.databinding.LayoutFeedUserBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.Uploader
import kotlinx.serialization.json.decodeFromJsonElement

class FeedUserViewHolder(
    private val binding: LayoutFeedUserBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<Uploader>(binding, jump) {

    override fun bind(card: Uploader) {
        binding.avatar.load(card.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = card.nick
        binding.desc.text = card.description
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FeedUserViewHolder {
            val binding =
                LayoutFeedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FeedUserViewHolder(binding, jump)
        }
    }
}