package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.recyclerview.layoutmanager.StackLayoutManager
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutTopicsSquareBinding
import com.ekko.page.databinding.LayoutTopicsSquareItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.TopicsPlayList
import com.ekko.repository.model.TopicsSquare

@PagingViewHolder(CardType.STACKED_SLIDE_COVER_IMAGE)
class StackedSlideCoverImageViewHolder(
    private val binding: LayoutTopicsSquareBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<TopicsSquare>>(binding) {

    override fun bind(card: MetroCard<TopicsSquare>, position: Int) {
        val data = card.metro_data
        val adapter = TopicSquareAdapter(data.item_list ?: return, jump)
        binding.list.layoutManager = StackLayoutManager()
//        if (binding.list.itemDecorationCount <= 0) {
//            binding.list.addItemDecoration(LineSpaceItemDecoration(8.dp))
//        }
        binding.list.adapter = adapter
    }
}

class TopicSquareAdapter(
    private val list: List<TopicsPlayList>,
    private val jump: (View, String) -> Unit
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
    private val jump: (View, String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TopicsPlayList) {
        binding.cover.apply {
          //  layoutParams.width = (data.cover?.img_info?.width?.toInt() ?: 500).coerceAtLeast(160.dp)
         //   layoutParams.height = layoutParams.width.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {
            crossfade(true)
        }
        binding.title.text = data.title
        binding.root.setOnClickListener {
            jump(it, data.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (View, String) -> Unit
        ): TopicSquareItemViewHolder {
            val binding = LayoutTopicsSquareItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return TopicSquareItemViewHolder(binding, jump)
        }
    }
}