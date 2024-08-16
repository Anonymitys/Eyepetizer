package com.ekko.playdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.ekko.play.detail.databinding.ViewHolderPlayContentBinding
import com.ekko.play.detail.databinding.ViewHolderPlayRecommendBinding
import com.ekko.repository.model.VideoItemCard

class ContentAdapter : ListAdapter<PlayItemCard, PlayViewHolder<out ViewBinding>>(COMPARATOR) {


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PlayItemCard>() {
            override fun areItemsTheSame(oldItem: PlayItemCard, newItem: PlayItemCard): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PlayItemCard,
                newItem: PlayItemCard
            ): Boolean {
                return oldItem.data.itemId == newItem.data.itemId
            }

        }

        const val RECOMMEND = 1001
        const val CONTENT = 1002
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayViewHolder<out ViewBinding> {
        return when (viewType) {
            RECOMMEND -> PlayRecommendViewHolder.create(parent)
            CONTENT -> PlayContentViewHolder.create(parent)
            else -> throw IllegalArgumentException("viewType error")
        }
    }

    override fun onBindViewHolder(holder: PlayViewHolder<out ViewBinding>, position: Int) {
        holder.bind(getItem(position).data)
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            "recommend" -> RECOMMEND
            "content" -> CONTENT
            else -> throw IllegalArgumentException("viewType 异常")
        }
    }
}


abstract class PlayViewHolder<T : ViewBinding>(binding: T) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(videoItemCard: VideoItemCard)
}


class PlayContentViewHolder(private val binding: ViewHolderPlayContentBinding) :
    PlayViewHolder<ViewHolderPlayContentBinding>(binding) {

    override fun bind(videoItemCard: VideoItemCard) {
        binding.title.text = videoItemCard.video?.title
        binding.desc.text = videoItemCard.text
        binding.avatar.load(videoItemCard.author?.avatar?.url){
            allowHardware(false)
        }
        binding.author.text = videoItemCard.author?.nick
        binding.authorDesc.text = videoItemCard.author?.description

    }

    companion object {
        fun create(parent: ViewGroup): PlayContentViewHolder {
            val binding = ViewHolderPlayContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return PlayContentViewHolder(binding)
        }
    }
}

class PlayRecommendViewHolder(private val binding: ViewHolderPlayRecommendBinding) :
    PlayViewHolder<ViewHolderPlayRecommendBinding>(binding) {

    override fun bind(videoItemCard: VideoItemCard) {
        val video = videoItemCard.video ?: return
        binding.cover.load(video.cover?.url) {
            crossfade(true)
        }
        binding.title.text = video.title
        binding.tag.text = video.tags.joinToString { it.title }
        binding.duration.text = video.duration?.text?.trim()
    }


    companion object {
        fun create(parent: ViewGroup): PlayRecommendViewHolder {
            val binding = ViewHolderPlayRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return PlayRecommendViewHolder(binding)
        }
    }
}


data class PlayItemCard(
    val type: String,
    val data: VideoItemCard
)