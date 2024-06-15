package com.ekko.page.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

/**
 * @Author Ekkoe
 * @Date 2023/9/28 15:22
 */
abstract class PageViewHolder<out T:Any>(
    binding: ViewBinding,
    jump: ((String) -> Unit)? = null
) : ViewHolder(binding.root) {
    abstract fun bind(card: @UnsafeVariance T)
}

abstract class SlideViewHolder<T>(
    binding: ViewBinding,
    jump: ((String) -> Unit)? = null
) : PageViewHolder<List<T>>(binding, jump)



