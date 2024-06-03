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
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val data = json.decodeFromJsonElement<GraphicCard>(card.data[0].metro_data)
        binding.cover.load(data.cover?.url)
        binding.title.text = data.title
        binding.consumption.text =
            "${data.consumption?.like_count}人点赞｜${data.consumption?.collection_count}人收藏"
        binding.root.setOnClickListener {
            jump(card.data[0].link)
        }
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