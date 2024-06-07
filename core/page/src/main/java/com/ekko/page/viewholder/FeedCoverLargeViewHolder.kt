package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.page.databinding.FeedCoverLargeItemBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import kotlinx.serialization.json.decodeFromJsonElement

class FeedCoverLargeViewHolder(
    private val binding: FeedCoverLargeItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedCoverVideo>(binding, jump) {
    override fun bind(card: FeedCoverVideo) {
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                itemView.context.screenWidth.div(card.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.cover?.url) {

        }
        binding.avatar.load(card.author?.avatar?.url) {
            transformations(CircleCropTransformation())
            crossfade(true)
        }
        binding.title.text = card.title
        binding.nickName.text = card.author?.nick
        binding.tag.text = card.tags?.joinToString { it.title }
        binding.duration.text = card.duration?.text
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FeedCoverLargeViewHolder {
            val binding = FeedCoverLargeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FeedCoverLargeViewHolder(binding, jump)
        }
    }
}