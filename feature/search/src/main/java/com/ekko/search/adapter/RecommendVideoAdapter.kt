package com.ekko.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.json
import com.ekko.repository.model.FeedCoverVideo
import com.ekko.repository.model.MetroCard
import com.ekko.search.databinding.LayoutRecommendVideoItemBinding
import kotlinx.serialization.json.decodeFromJsonElement

class RecommendVideoAdapter(private val data: List<MetroCard>) : Adapter<RecommendVideoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendVideoViewHolder = RecommendVideoViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: RecommendVideoViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class RecommendVideoViewHolder(private val binding: LayoutRecommendVideoItemBinding) : ViewHolder(
    binding.root
) {

    fun bind(data: MetroCard) {
        val start = System.currentTimeMillis()
        Log.e("huqiang", "bind: $start")
        val video = json.decodeFromJsonElement<FeedCoverVideo>(data.metro_data)
        binding.cover.load(video.cover?.url) {
            crossfade(true)
            transformations(RoundedCornersTransformation(4.dp.toFloat()))
        }
        binding.title.text = video.title
        Log.e("huqiang", "bind: ${System.currentTimeMillis() - start}")
    }

    companion object {
        fun create(parent: ViewGroup): RecommendVideoViewHolder {
            val binding = LayoutRecommendVideoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return RecommendVideoViewHolder(binding)
        }
    }
}