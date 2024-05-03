package com.ekko.page.viewholder

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.ekko.page.model.ItemCard

/**
 * @Author Ekkoe
 * @Date 2023/9/28 15:22
 */
abstract class PageViewHolder(
    binding: ViewBinding,
    jump: ((String) -> Unit)? = null
) : ViewHolder(binding.root) {
    abstract fun bind(card: ItemCard)
}


