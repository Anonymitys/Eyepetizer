package com.ekko.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekko.search.databinding.LayoutRecommendKeyBinding

class RecommendKeyAdapter(
    private val data: List<String>,
    private val click: (String) -> Unit
) : Adapter<RecommendKeyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendKeyViewHolder = RecommendKeyViewHolder.create(parent, click)

    override fun onBindViewHolder(
        holder: RecommendKeyViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class RecommendKeyViewHolder(
    private val binding: LayoutRecommendKeyBinding,
    private val click: (String) -> Unit
) : ViewHolder(binding.root) {

    fun bind(key: String) {
        binding.key.text = key
        binding.root.setOnClickListener { click(key) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            click: (String) -> Unit
        ): RecommendKeyViewHolder {
            val binding = LayoutRecommendKeyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return RecommendKeyViewHolder(binding, click)
        }
    }
}