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
    PageViewHolder(binding,jump) {

    override fun bind(card: ItemCard) {
        val pgcCard = json.decodeFromJsonElement<CommunityPgcCard>(card.data[0].metro_data)
        binding.avatar.load(pgcCard.author?.avatar?.url) {
            transformations(CircleCropTransformation())
        }
        binding.nickName.text = pgcCard.author?.nick
        binding.publishTime.text = pgcCard.publish_time
        binding.collect.isVisible = pgcCard.show_follow_btn
        binding.cover.apply {
            layoutParams.width = context.screenWidth
            layoutParams.height =
                layoutParams.width.div(pgcCard.video?.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(pgcCard.video?.cover?.url) {
            crossfade(true)
        }

        binding.desc.text = pgcCard.text
        binding.tag.text = pgcCard.topics?.joinToString { it.title }

        binding.like.text = pgcCard.consumption?.like_count.toString()
        binding.collected.text = pgcCard.consumption?.collection_count.toString()
        binding.comment.text = pgcCard.consumption?.comment_count.toString()
        binding.root.setOnClickListener {
            jump.invoke(card.data[0].link)
        }
    }

    companion object {
        fun create(parent: ViewGroup,jump:(String)->Unit): CommunityPgcVideoViewHolder {
            val binding =
                CommunityPgcCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CommunityPgcVideoViewHolder(binding,jump)
        }
    }
}