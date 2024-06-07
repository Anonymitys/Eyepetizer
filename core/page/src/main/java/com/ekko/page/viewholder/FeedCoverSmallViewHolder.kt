package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.page.databinding.FeedCoverSmallItemBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import kotlinx.serialization.json.decodeFromJsonElement

class FeedCoverSmallViewHolder(
    private val binding: FeedCoverSmallItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedCoverVideo>(binding, jump) {

    override fun bind(card: FeedCoverVideo) {
        binding.cover.apply {
            layoutParams.width = (itemView.context.screenWidth - 32.dp) / 2
            layoutParams.height = layoutParams.width.div(card.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.cover?.url) {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(
                    topLeft = 4.dp.toFloat(),
                    bottomLeft = 4.dp.toFloat()
                )
            )
        }
        binding.title.text = card.title
        binding.tag.text = card.tags?.joinToString { it.title }
        binding.duration.text = card.duration?.text?.trim()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FeedCoverSmallViewHolder {
            val binding = FeedCoverSmallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FeedCoverSmallViewHolder(binding, jump)
        }
    }
}