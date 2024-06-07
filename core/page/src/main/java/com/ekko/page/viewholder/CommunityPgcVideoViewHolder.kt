package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.page.databinding.CommunityPgcCardBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.CommunityPgcCard
import kotlinx.serialization.json.decodeFromJsonElement

class CommunityPgcVideoViewHolder(private val binding: CommunityPgcCardBinding, private val jump:(String)->Unit) :
    PageViewHolder<CommunityPgcCard>(binding,jump) {

    override fun bind(card: CommunityPgcCard) {
        binding.avatar.load(card.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = card.author?.nick
        binding.publishTime.text = card.publish_time
        binding.collect.isVisible = card.show_follow_btn
        binding.cover.apply {
            layoutParams.width = context.screenWidth
            layoutParams.height =
                layoutParams.width.div(card.video?.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.video?.cover?.url) {
            crossfade(true)
        }

        binding.desc.text = card.text
        binding.tag.text = card.topics?.joinToString { it.title }

        binding.like.text = card.consumption?.like_count.toString()
        binding.collected.text = card.consumption?.collection_count.toString()
        binding.comment.text = card.consumption?.comment_count.toString()
    }

    companion object {
        fun create(parent: ViewGroup,jump:(String)->Unit): CommunityPgcVideoViewHolder {
            val binding =
                CommunityPgcCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CommunityPgcVideoViewHolder(binding,jump)
        }
    }
}