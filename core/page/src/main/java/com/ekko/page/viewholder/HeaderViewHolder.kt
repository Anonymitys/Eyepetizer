package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ekko.page.databinding.LayoutPageHeaderItemBinding
import com.ekko.repository.model.Layout
import kotlinx.serialization.json.jsonPrimitive

class HeaderViewHolder(
    private val binding: LayoutPageHeaderItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<Layout>(binding) {

    override fun bind(card: Layout) {
        val title = card.left?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "text"
        )?.jsonPrimitive?.content ?: ""
        val link = card.right?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "link"
        )?.jsonPrimitive?.content ?: ""
        binding.title.text = title
        binding.more.isVisible = link.isNotEmpty()
        binding.more.setOnClickListener {
            jump(link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): HeaderViewHolder {
            val binding =
                LayoutPageHeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            return HeaderViewHolder(binding, jump)
        }
    }
}