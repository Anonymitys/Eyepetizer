package com.ekko.eyepetizer.page

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ekko.eyepetizer.page.viewholder.CommunityPgcVideoViewHolder
import com.ekko.eyepetizer.page.viewholder.DefaultViewHolder
import com.ekko.eyepetizer.page.viewholder.FeedCoverLargeViewHolder
import com.ekko.eyepetizer.page.viewholder.FeedCoverSmallViewHolder
import com.ekko.eyepetizer.page.viewholder.PageViewHolder
import com.ekko.eyepetizer.page.viewholder.SlideCoverWithFooterViewHolder
import com.ekko.eyepetizer.page.viewholder.ViewType
import com.ekko.eyepetizer.page.viewholder.WaterfallCoverSmallImageViewHolder
import com.ekko.eyepetizer.page.viewholder.WaterfallCoverSmallVideoViewHolder

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/28 15:14
 */
class PageAdapter() : PagingDataAdapter<ItemCard, PageViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PageViewHolder = ViewHolderFactory.create(viewType, parent)

    override fun onBindViewHolder(
        holder: PageViewHolder,
        position: Int,
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return convertCardType2ViewType(getItem(position)?.type ?: "")
    }

    private fun convertCardType2ViewType(cardType: String): Int {
        return when (cardType) {
            CardType.FEED_COVER_LARGE_VIDEO -> ViewType.FEED_COVER_LARGE_VIDEO
            CardType.FEED_COVER_SMALL_VIDEO -> ViewType.FEED_COVER_SMALL_VIDEO
            CardType.SLIDE_COVER_IMAGE_WITH_FOOTER -> ViewType.SLIDE_COVER_IMAGE_WITH_FOOTER
            CardType.WATERFALL_COVER_SMALL_IMAGE -> ViewType.WATERFALL_COVER_SMALL_IMAGE
            CardType.WATERFALL_COVER_SMALL_VIDEO -> ViewType.WATERFALL_COVER_SMALL_VIDEO
            CardType.FEED_ITEM_DETAIL -> ViewType.FEED_ITEM_DETAIL
            else -> ViewType.DEFAULT
        }
    }

    fun convertViewType2SpanSize(position: Int): Int {
        return when (getItemViewType(position)) {
            ViewType.WATERFALL_COVER_SMALL_IMAGE, ViewType.WATERFALL_COVER_SMALL_VIDEO -> 1
            else -> 2
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ItemCard>() {
            override fun areContentsTheSame(
                oldItem: ItemCard,
                newItem: ItemCard,
            ): Boolean {
                return oldItem.data[0].metro_id == newItem.data[0].metro_id
            }

            override fun areItemsTheSame(
                oldItem: ItemCard,
                newItem: ItemCard,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

object ViewHolderFactory {

    fun create(viewType: Int, parent: ViewGroup): PageViewHolder {
        return when (viewType) {
            ViewType.FEED_COVER_LARGE_VIDEO -> FeedCoverLargeViewHolder.create(parent)
            ViewType.FEED_COVER_SMALL_VIDEO -> FeedCoverSmallViewHolder.create(parent)
            ViewType.SLIDE_COVER_IMAGE_WITH_FOOTER -> SlideCoverWithFooterViewHolder.create(parent)
            ViewType.WATERFALL_COVER_SMALL_IMAGE -> WaterfallCoverSmallImageViewHolder.create(parent)
            ViewType.WATERFALL_COVER_SMALL_VIDEO -> WaterfallCoverSmallVideoViewHolder.create(parent)
            ViewType.FEED_ITEM_DETAIL -> CommunityPgcVideoViewHolder.create(parent)
            else -> DefaultViewHolder.create(parent)
        }
    }


}



