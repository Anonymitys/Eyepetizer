package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import coil.load
import com.ekko.base.json
import com.ekko.page.databinding.LayoutGraphicItemBinding
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.GraphicCard
import kotlinx.serialization.json.decodeFromJsonElement

class GraphicViewHolder(
    private val binding: LayoutGraphicItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<GraphicCard>(binding, jump) {

    override fun bind(card: GraphicCard) {
        binding.cover.load(card.cover?.url)
        binding.title.text = card.title
        binding.consumption.text =
            "${card.consumption?.like_count}人点赞｜${card.consumption?.collection_count}人收藏"
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): GraphicViewHolder {
            val binding =
                LayoutGraphicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GraphicViewHolder(binding, jump)
        }
    }
}