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
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val user = json.decodeFromJsonElement<Uploader>(card.data[0].metro_data)
        binding.avatar.load(user.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = user.nick
        binding.desc.text = user.description
        binding.root.setOnClickListener {
            jump(user.link)
        }
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