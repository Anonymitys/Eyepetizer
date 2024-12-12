package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.ekko.base.recyclerview.layoutmanager.StackLayoutManager
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutSlideCoverVideoBinding
import com.ekko.page.databinding.LayoutSlideCoverVideoItemBinding
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.MetroCard


@PagingViewHolder(CardType.SLIDE_COVER_VIDEO)
class SlideCoverVideo(
    private val binding: LayoutSlideCoverVideoBinding,
    private val jump: (View, String) -> Unit
) : SlideViewHolder<MetroCard<FeedCoverVideo>>(binding, jump) {

    override fun bind(card: List<MetroCard<FeedCoverVideo>>, position: Int) {
        binding.list.layoutManager = StackLayoutManager()
        binding.list.adapter = SlideCoverVideoAdapter(card.map { it.metro_data }, jump)
    }
}


class SlideCoverVideoAdapter(
    private val data: List<FeedCoverVideo>,
    private val jump: (View, String) -> Unit
) :
    Adapter<SlideCoverVideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideCoverVideoViewHolder {
        return SlideCoverVideoViewHolder.create(parent, jump)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SlideCoverVideoViewHolder, position: Int) {
        holder.bind(data[position])
    }

}


class SlideCoverVideoViewHolder(
    private val binding: LayoutSlideCoverVideoItemBinding,
    private val jump: (View, String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedCoverVideo) {
        binding.cover.apply {
//            layoutParams.width = itemView.context.screenWidth
//            layoutParams.height =
//                itemView.context.screenWidth.div(item.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(item.cover?.url) {

        }
    }

    companion object {
        fun create(parent: ViewGroup, jump: (View, String) -> Unit): SlideCoverVideoViewHolder {
            val binding = LayoutSlideCoverVideoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SlideCoverVideoViewHolder(binding, jump)
        }
    }
}


