package com.ekko.page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.page.databinding.LayoutTopicsItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.TopicsPlayList

class TopicsAdapter(
    private val data: List<MetroCard<TopicsPlayList>>,
    private val jump: (View, String) -> Unit
) : RecyclerView.Adapter<TopicsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicsViewHolder = TopicsViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: TopicsViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class TopicsViewHolder(
    private val binding: LayoutTopicsItemBinding,
    private val jump: (View, String) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(card: MetroCard<TopicsPlayList>) {
        val data = card.metro_data
        binding.cover.apply {
            layoutParams.width = data.cover?.img_info?.width?.toInt() ?: 500
            layoutParams.height = layoutParams.width.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {
            crossfade(true)
        }
        binding.title.isVisible = data.title.isNotEmpty()
        binding.title.text = data.title
        binding.root.setOnClickListener {
            jump(it, card.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (View, String) -> Unit
        ): TopicsViewHolder {
            val binding =
                LayoutTopicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopicsViewHolder(binding, jump)
        }
    }
}