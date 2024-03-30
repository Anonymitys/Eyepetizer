package com.ekko.eyepetizer.page.viewholder

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.ekko.eyepetizer.page.ItemCard

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

object ViewType {
    const val FEED_COVER_LARGE_VIDEO = 1001
    const val FEED_COVER_SMALL_VIDEO = 1002
    const val SLIDE_COVER_IMAGE_WITH_FOOTER = 1003
    const val WATERFALL_COVER_SMALL_IMAGE = 1004
    const val WATERFALL_COVER_SMALL_VIDEO = 1005
    const val FEED_ITEM_DETAIL = 1006
    const val ICON_GRID = 1007
    const val DEFAULT = 100000
}


