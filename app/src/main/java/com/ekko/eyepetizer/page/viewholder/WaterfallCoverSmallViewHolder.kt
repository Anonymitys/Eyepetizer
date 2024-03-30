package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.eyepetizer.databinding.WaterfallCoverSmallBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.WaterfallCoverImage
import kotlinx.serialization.json.decodeFromJsonElement

class WaterfallCoverSmallImageViewHolder(
    private val binding: WaterfallCoverSmallBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val waterfallCoverImage =
            json.decodeFromJsonElement<WaterfallCoverImage>(card.data[0].metro_data)
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth / 2
            layoutParams.height = layoutParams.width
        }.load(waterfallCoverImage.cover?.url) {
            crossfade(true)
        }

        binding.title.text = waterfallCoverImage.title
        binding.avatar.load(waterfallCoverImage.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = waterfallCoverImage.author?.nick
        binding.root.setOnClickListener {
            jump.invoke(card.data[0].link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup?,
            jump: (String) -> Unit
        ): WaterfallCoverSmallImageViewHolder {
            val binding = WaterfallCoverSmallBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            return WaterfallCoverSmallImageViewHolder(binding, jump)
        }
    }
}

class WaterfallCoverSmallVideoViewHolder(
    private val binding: WaterfallCoverSmallBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val waterfallCoverVideo =
            json.decodeFromJsonElement<FeedCoverVideo>(card.data[0].metro_data)
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth / 2
            layoutParams.height = layoutParams.width
        }.load(waterfallCoverVideo.cover?.url) {
            crossfade(true)
        }

        binding.title.text = waterfallCoverVideo.title
        binding.avatar.load(waterfallCoverVideo.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = waterfallCoverVideo.author?.nick
        binding.root.setOnClickListener {
            jump.invoke(card.data[0].link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup?,
            jump: (String) -> Unit
        ): WaterfallCoverSmallVideoViewHolder {
            val binding = WaterfallCoverSmallBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            return WaterfallCoverSmallVideoViewHolder(binding, jump)
        }
    }
}