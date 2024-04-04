package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.eyepetizer.databinding.LayoutTopicsItemBinding
import com.ekko.eyepetizer.databinding.LayoutTopicsListBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.TopicsPlayList
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

class TopicsListViewHolder(
    private val binding: LayoutTopicsListBinding,
    private val jump: (String) -> Unit
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val title = card.header?.left?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "text"
        )?.jsonPrimitive?.content
        val link = card.header?.right?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "link"
        )?.jsonPrimitive?.content ?: ""
        binding.title.text = title
        binding.more.setOnClickListener {
            jump(link)
        }
        binding.more.visibility = if (link.isEmpty()) View.GONE else View.VISIBLE

        val adapter = TopicsAdapter(card.data, jump)
        binding.list.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        if (binding.list.itemDecorationCount <= 0) {
            binding.list.addItemDecoration(LineSpaceItemDecoration(16.dp))
        }
        binding.list.adapter = adapter
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): TopicsListViewHolder {
            val binding =
                LayoutTopicsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopicsListViewHolder(binding, jump)
        }
    }
}

class TopicsAdapter(
    private val data: List<MetroCard>,
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

    fun bind(card: MetroCard) {
        val data = json.decodeFromJsonElement<TopicsPlayList>(card.metro_data)
        binding.cover.apply {
            layoutParams.width = data.cover?.img_info?.width?.toInt() ?: 500
            layoutParams.height = layoutParams.width.div(data.cover?.img_info?.scale ?: 1.0).toInt()
        }.load(data.cover?.url) {
            crossfade(true)
        }
        binding.title.text = data.title
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