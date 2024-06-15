package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.WaterfallCoverSmallBinding
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.WaterfallCoverImage

@PagingViewHolder(CardType.WATERFALL_COVER_SMALL_IMAGE)
class WaterfallCoverSmallImageViewHolder(
    private val binding: WaterfallCoverSmallBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<WaterfallCoverImage>(binding, jump) {

    override fun bind(card: WaterfallCoverImage) {
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth / 2
            layoutParams.height = layoutParams.width
        }.load(card.cover?.url) {
            crossfade(true)
        }

        binding.title.text = card.title
        binding.avatar.load(card.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = card.author?.nick
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
@PagingViewHolder(CardType.WATERFALL_COVER_SMALL_VIDEO)
class WaterfallCoverSmallVideoViewHolder(
    private val binding: WaterfallCoverSmallBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder<FeedCoverVideo>(binding, jump) {

    override fun bind(card: FeedCoverVideo) {
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth / 2
            layoutParams.height = layoutParams.width
        }.load(card.cover?.url) {
            crossfade(true)
        }

        binding.title.text = card.title
        binding.avatar.load(card.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = card.author?.nick
    }
}