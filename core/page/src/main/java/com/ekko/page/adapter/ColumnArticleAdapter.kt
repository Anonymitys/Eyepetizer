package com.ekko.page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.page.databinding.LayoutTopicsItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.TopicsPlayList

class ColumnArticleAdapter(
    private val data: List<MetroCard<TopicsPlayList>>,
    private val jump: (View, String) -> Unit
) : RecyclerView.Adapter<ColumnArticleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColumnArticleViewHolder = ColumnArticleViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: ColumnArticleViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class ColumnArticleViewHolder(
    private val binding: LayoutTopicsItemBinding,
    private val jump: (View, String) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(card: MetroCard<TopicsPlayList>) {
        val data = card.metro_data
        binding.cover.apply {
            // layoutParams.width = data.cover?.img_info?.width?.toInt() ?: 500
            layoutParams.width = context.screenWidth - 100.dp
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
        ): ColumnArticleViewHolder {
            val binding =
                LayoutTopicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ColumnArticleViewHolder(binding, jump)
        }
    }
}