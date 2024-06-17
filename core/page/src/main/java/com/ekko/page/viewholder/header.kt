package com.ekko.page.viewholder

import androidx.core.view.isVisible
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutPageHeaderItemBinding
import com.ekko.repository.model.Layout
import kotlinx.serialization.json.jsonPrimitive

@PagingViewHolder(CardType.HEADER)
class HeaderViewHolder(
    private val binding: LayoutPageHeaderItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<Layout>(binding) {

    override fun bind(
        card: Layout,
        position: Int
    ) {
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
}