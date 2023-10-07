package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.eyepetizer.databinding.DefaultItemBinding
import com.ekko.eyepetizer.databinding.FeedCoverSmallItemBinding
import com.ekko.eyepetizer.databinding.SlideCoverWithFooterItemBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.FeedCoverVideo
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * @Author Ekkoe
 * @Date 2023/9/28 15:22
 */
abstract class PageViewHolder(binding: ViewBinding) : ViewHolder(binding.root) {
    abstract fun bind(card: ItemCard)
}

object ViewType {
    const val FEED_COVER_LARGE_VIDEO = 1001
    const val FEED_COVER_SMALL_VIDEO = 1002
    const val SLIDE_COVER_IMAGE_WITH_FOOTER = 1003
    const val WATERFALL_COVER_SMALL_IMAGE = 1004
    const val WATERFALL_COVER_SMALL_VIDEO = 1005
    const val FEED_ITEM_DETAIL = 1006
    const val DEFAULT = 100000
}


