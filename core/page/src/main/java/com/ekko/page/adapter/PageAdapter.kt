package com.ekko.page.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ekko.page.CardType
import com.ekko.page.ViewType
import com.ekko.page.model.ItemCard
import com.ekko.page.viewholder.CommunityPgcVideoViewHolder
import com.ekko.page.viewholder.FeedCoverLargeViewHolder
import com.ekko.page.viewholder.FeedCoverSmallViewHolder
import com.ekko.page.viewholder.IconGridViewHolder
import com.ekko.page.viewholder.SlideCoverWithFooterViewHolder
import com.ekko.page.viewholder.TopicSquareViewHolder
import com.ekko.page.viewholder.TopicsListViewHolder
import com.ekko.page.viewholder.WaterfallCoverSmallImageViewHolder
import com.ekko.page.viewholder.WaterfallCoverSmallVideoViewHolder

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/28 15:14
 */
class PageAdapter(private val jump: (String) -> Unit) :
    PagingDataAdapter<ItemCard, com.ekko.page.viewholder.PageViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): com.ekko.page.viewholder.PageViewHolder = ViewHolderFactory.create(viewType, parent, jump)

    override fun onBindViewHolder(
        holder: com.ekko.page.viewholder.PageViewHolder,
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
            CardType.ICON_GRID -> ViewType.ICON_GRID
            CardType.SLIDE_COVER_IMAGE_WITH_TITLE, CardType.SLIDE_COVER_IMAGE -> ViewType.SLIDE_COVER_IMAGE_WITH_TITLE
            CardType.STACKED_SLIDE_COVER_IMAGE -> ViewType.STACKED_SLIDE_COVER_IMAGE
            else -> ViewType.DEFAULT
        }
    }

    fun convertViewType2SpanSize(position: Int): Int {
        if (position >= itemCount) return 2
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

    fun create(
        viewType: Int,
        parent: ViewGroup,
        jump: (String) -> Unit
    ): com.ekko.page.viewholder.PageViewHolder {
        return when (viewType) {
            ViewType.FEED_COVER_LARGE_VIDEO -> FeedCoverLargeViewHolder.create(parent, jump)
            ViewType.FEED_COVER_SMALL_VIDEO -> FeedCoverSmallViewHolder.create(parent, jump)
            ViewType.SLIDE_COVER_IMAGE_WITH_FOOTER -> SlideCoverWithFooterViewHolder.create(
                parent, jump
            )

            ViewType.WATERFALL_COVER_SMALL_IMAGE -> WaterfallCoverSmallImageViewHolder.create(
                parent, jump
            )

            ViewType.WATERFALL_COVER_SMALL_VIDEO -> WaterfallCoverSmallVideoViewHolder.create(
                parent, jump
            )

            ViewType.FEED_ITEM_DETAIL -> CommunityPgcVideoViewHolder.create(parent, jump)
            ViewType.ICON_GRID -> IconGridViewHolder.create(parent, jump)
            ViewType.SLIDE_COVER_IMAGE_WITH_TITLE -> TopicsListViewHolder.create(
                parent, jump
            )

            ViewType.STACKED_SLIDE_COVER_IMAGE -> TopicSquareViewHolder.create(parent, jump)

            else -> com.ekko.page.viewholder.DefaultViewHolder.create(parent)
        }
    }
}



