package com.ekko.search.adapter

import android.view.LayoutInflater
import android.view.View
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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

class RecommendVideoAdapter(
    private val data: List<MetroCard<JsonObject>>,
    private val jump: (View, String) -> Unit
) : Adapter<RecommendVideoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendVideoViewHolder = RecommendVideoViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: RecommendVideoViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class RecommendVideoViewHolder(
    private val binding: LayoutRecommendVideoItemBinding,
    private val jump: (View, String) -> Unit
) : ViewHolder(
    binding.root
) {

    fun bind(data: MetroCard<JsonObject>) {
        val video = json.decodeFromJsonElement<FeedCoverVideo>(data.metro_data)
        binding.cover.load(video.cover?.url) {
            crossfade(true)
            transformations(RoundedCornersTransformation(4.dp.toFloat()))
        }
        binding.title.text = video.title
        binding.root.setOnClickListener {
            jump(it, data.link)
        }
    }

    companion object {
        fun create(parent: ViewGroup, jump: (View, String) -> Unit): RecommendVideoViewHolder {
            val binding = LayoutRecommendVideoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return RecommendVideoViewHolder(binding, jump)
        }
    }
}