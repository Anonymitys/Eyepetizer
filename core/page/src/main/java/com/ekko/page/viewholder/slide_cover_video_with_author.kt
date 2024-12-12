package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutCoverVideoWithAuthorItemBinding
import com.ekko.page.databinding.SlideCoverVideoWithAuthorBinding
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.SLIDE_COVER_VIDEO_WITH_AUTHOR)
class SlideCoverVideoWithAuthor(
    private val binding: SlideCoverVideoWithAuthorBinding,
    private val jump: (View, String) -> Unit
) : SlideViewHolder<MetroCard<FeedCoverVideo>>(binding, jump) {
    override fun bind(card: List<MetroCard<FeedCoverVideo>>, position: Int) {
        binding.list.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        binding.list.adapter = CoverVideoWithAuthorAdapter(card.map { it.metro_data }, jump)
        if (binding.list.itemDecorationCount <= 0) {
            binding.list.addItemDecoration(LineSpaceItemDecoration(8.dp))
        }
    }
}

class CoverVideoWithAuthorAdapter(
    private val data: List<FeedCoverVideo>,
    private val jump: (View, String) -> Unit
) :
    RecyclerView.Adapter<CoverVideoWithAuthorViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoverVideoWithAuthorViewHolder {
        return CoverVideoWithAuthorViewHolder.create(parent, jump)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CoverVideoWithAuthorViewHolder, position: Int) {
        holder.bind(data[position])
    }
}

class CoverVideoWithAuthorViewHolder(
    private val binding: LayoutCoverVideoWithAuthorItemBinding,
    private val jump: (View, String) -> Unit
) : ViewHolder(binding.root) {


    fun bind(video: FeedCoverVideo) {
        binding.cover.apply {
            layoutParams.width = itemView.context.screenWidth.times(0.8).toInt()
            layoutParams.height =
                layoutParams.width.div(video.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(video.cover?.url) {

        }
        binding.avatar.load(video.author?.avatar?.url) {
            crossfade(true)
        }
        binding.title.text = video.title
        binding.nickName.text = video.author?.nick
        binding.tag.text = video.tags?.joinToString { it.title }
        binding.duration.text = video.duration?.text
        binding.avatar.setOnClickListener {
            jump(it, video.author?.link ?: "")
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (View, String) -> Unit
        ): CoverVideoWithAuthorViewHolder {
            val binding = LayoutCoverVideoWithAuthorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CoverVideoWithAuthorViewHolder(binding, jump)
        }
    }
}