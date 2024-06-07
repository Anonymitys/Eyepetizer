package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ekko.page.databinding.LayoutPageFooterItemBinding

class FooterViewHolder(
    private val binding: LayoutPageFooterItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<String>(binding) {

    override fun bind(card: String) {
        //
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): FooterViewHolder {
            val binding =
                LayoutPageFooterItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            return FooterViewHolder(binding, jump)
        }
    }
}