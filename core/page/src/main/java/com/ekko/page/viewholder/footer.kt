package com.ekko.page.viewholder

import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutPageFooterItemBinding

@PagingViewHolder(CardType.FOOTER)
class FooterViewHolder(
    private val binding: LayoutPageFooterItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<String>(binding) {

    override fun bind(
        card: String,
        position: Int
    ) {
        //
    }
}