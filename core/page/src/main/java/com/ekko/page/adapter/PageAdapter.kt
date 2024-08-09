package com.ekko.page.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ekko.base.ktx.json
import com.ekko.page.CardType
import com.ekko.page.model.FooterItemCard
import com.ekko.page.model.HeaderItemCard
import com.ekko.page.model.ItemCard
import com.ekko.page.model.MetroItemCard
import com.ekko.page.model.SlideItemCard
import com.ekko.page.viewholder.Converter
import com.ekko.page.viewholder.DefaultViewHolder
import com.ekko.page.viewholder.PageViewHolder
import com.ekko.page.viewholder.ViewHolderFactory
import com.ekko.repository.model.toMetroCard
import kotlinx.serialization.serializer

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/28 15:14
 */
class PageAdapter(private val jump: (View, String) -> Unit) :
    PagingDataAdapter<ItemCard, PageViewHolder<Any>>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PageViewHolder<Any> = ViewHolderFactory.create(viewType, parent, jump)

    override fun onBindViewHolder(
        holder: PageViewHolder<Any>,
        position: Int,
    ) {
        if (holder is DefaultViewHolder) return
        getItem(position)?.let { card ->
            when (card) {
                is MetroItemCard -> {
                    val type =
                        holder::class.supertypes[0].arguments[0].type?.arguments?.get(0)?.type
                            ?: return
                    holder.bind(card.data.toMetroCard(type), card.index)
                    holder.itemView.setOnClickListener {
                        jump(holder.itemView, card.data.link)
                    }
                }

                is SlideItemCard -> {
                    val type =
                        holder::class.supertypes[0].arguments[0].type?.arguments?.get(0)?.type
                            ?: return
                    val data = card.data.map {
                        it.toMetroCard(type)
                    }
                    holder.bind(data, 0)
                }

                is FooterItemCard -> {
                    holder.bind("", 0)
                }

                is HeaderItemCard -> {
                    holder.bind(card.left, 0)
                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return Converter.convertCardType2ViewType(getItem(position)?.type ?: "")
    }

    fun convertViewType2SpanSize(
        position: Int,
        spanCount: Int
    ): Int {
        if (position >= itemCount) return spanCount
        return when (getItemViewType(position)) {
            CardType.WATERFALL_COVER_SMALL_IMAGE.hashCode(), CardType.WATERFALL_COVER_SMALL_VIDEO.hashCode() -> 1
            else -> spanCount
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ItemCard>() {
            override fun areContentsTheSame(
                oldItem: ItemCard,
                newItem: ItemCard,
            ): Boolean {
                return oldItem.uniqueId == newItem.uniqueId
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



