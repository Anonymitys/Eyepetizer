package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.eyepetizer.databinding.FeedCoverSmallItemBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import kotlinx.serialization.json.decodeFromJsonElement

class FeedCoverSmallViewHolder(private val binding: FeedCoverSmallItemBinding) :
    PageViewHolder(binding) {

    override fun bind(card: ItemCard) {
        val data = json.decodeFromJsonElement<FeedCoverVideo>(card.data[0].metro_data)
        binding.cover.apply {
            layoutParams.width = (itemView.context.screenWidth - 32.dp) / 2
            layoutParams.height = layoutParams.width.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(
                    topLeft = 4.dp.toFloat(),
                    bottomLeft = 4.dp.toFloat()
                )
            )
        }
        binding.title.text = data.title
        binding.tag.text = data.tags?.joinToString { it.title }
        binding.duration.text = data.duration?.text?.trim()
    }

    companion object {
        fun create(parent: ViewGroup): FeedCoverSmallViewHolder {
            val binding = FeedCoverSmallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FeedCoverSmallViewHolder(binding)
        }
    }

}