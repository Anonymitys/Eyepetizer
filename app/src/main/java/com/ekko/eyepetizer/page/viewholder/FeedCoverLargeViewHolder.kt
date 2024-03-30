package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.eyepetizer.databinding.FeedCoverLargeItemBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import kotlinx.serialization.json.decodeFromJsonElement

class FeedCoverLargeViewHolder(
    private val binding: FeedCoverLargeItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder(binding, jump) {
    override fun bind(card: ItemCard) {
        val data = json.decodeFromJsonElement<FeedCoverVideo>(card.data[0].metro_data)
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                itemView.context.screenWidth.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {

        }
        binding.avatar.load(data.author?.avatar?.url) {
            transformations(CircleCropTransformation())
            crossfade(true)
        }
        binding.title.text = data.title
        binding.nickName.text = data.author?.nick
        binding.tag.text = data.tags?.joinToString { it.title }
        binding.duration.text = data.duration?.text
        binding.root.setOnClickListener {
            jump.invoke(card.data[0].link)
        }
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