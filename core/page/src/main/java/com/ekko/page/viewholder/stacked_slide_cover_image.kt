package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.dp
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutTopicsSquareBinding
import com.ekko.page.databinding.LayoutTopicsSquareItemBinding
import com.ekko.repository.model.TopicsPlayList
import com.ekko.repository.model.TopicsSquare

@PagingViewHolder(CardType.STACKED_SLIDE_COVER_IMAGE)
class StackedSlideCoverImageViewHolder(
    private val binding: LayoutTopicsSquareBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<TopicsSquare>(binding) {

    override fun bind(card: TopicsSquare) {
        val adapter = TopicSquareAdapter(card.item_list ?: return, jump)
        binding.list.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        if (binding.list.itemDecorationCount <= 0) {
            binding.list.addItemDecoration(LineSpaceItemDecoration(16.dp))
        }
        binding.list.adapter = adapter
    }
}

class TopicSquareAdapter(
    private val list: List<TopicsPlayList>,
    private val jump: (String) -> Unit
) : RecyclerView.Adapter<TopicSquareItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicSquareItemViewHolder = TopicSquareItemViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: TopicSquareItemViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class TopicSquareItemViewHolder(
    private val binding: LayoutTopicsSquareItemBinding,
    private val jump: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TopicsPlayList) {
        binding.cover.apply {
            layoutParams.width = (data.cover?.img_info?.width?.toInt() ?: 500).coerceAtLeast(160.dp)
            layoutParams.height = layoutParams.width.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {
            crossfade(true)
        }
        binding.title.text = data.title
        binding.root.setOnClickListener {
            jump(data.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): TopicSquareItemViewHolder {
            val binding = LayoutTopicsSquareItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return TopicSquareItemViewHolder(binding, jump)
        }
    }
}