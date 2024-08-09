package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.WaterfallCoverSmallBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.WaterfallCoverImage

@PagingViewHolder(CardType.WATERFALL_COVER_SMALL_IMAGE)
class WaterfallCoverSmallImageViewHolder(
    private val binding: WaterfallCoverSmallBinding,
    private val jump: (View, String) -> Unit
) :
    PageViewHolder<MetroCard<WaterfallCoverImage>>(binding, jump) {

    override fun bind(card: MetroCard<WaterfallCoverImage>, position: Int) {
        val data = card.metro_data
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth / 2 - 12.dp
            layoutParams.height = layoutParams.width
        }.load(data.cover?.url) {
            crossfade(true)
        }
        val params = itemView.layoutParams as MarginLayoutParams
        when ((position + 1) % 2) {
            0 -> {
                params.marginStart = 4.dp
                params.marginEnd = 8.dp
            }

            1 -> {
                params.marginStart = 8.dp
                params.marginEnd = 4.dp
            }
        }
        binding.title.text = data.title
        binding.avatar.load(data.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = data.author?.nick
    }

    companion object {
        fun create(
            parent: ViewGroup?,
            jump: (View, String) -> Unit
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