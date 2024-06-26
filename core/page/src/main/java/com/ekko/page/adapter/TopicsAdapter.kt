package com.ekko.page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.page.databinding.LayoutTopicsItemBinding
import com.ekko.repository.model.TopicsPlayList

class TopicsAdapter(
    private val data: List<TopicsPlayList>,
    private val jump: (String) -> Unit
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
    private val jump: (String) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(card: TopicsPlayList) {
        binding.cover.apply {
            layoutParams.width = card.cover?.img_info?.width?.toInt() ?: 500
            layoutParams.height = layoutParams.width.div(card.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(card.cover?.url) {
            crossfade(true)
        }
        binding.title.text = card.title
        binding.root.setOnClickListener {
            jump(card.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): TopicsViewHolder {
            val binding =
                LayoutTopicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopicsViewHolder(binding, jump)
        }
    }
}