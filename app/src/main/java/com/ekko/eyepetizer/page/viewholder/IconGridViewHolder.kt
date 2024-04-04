package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.recyclerview.decoration.GridSpaceItemDecoration
import com.ekko.eyepetizer.databinding.LayoutIconGridItemBinding
import com.ekko.eyepetizer.databinding.LayoutIconItemBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.Icon
import com.ekko.repository.model.Icons
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

class IconGridViewHolder(
    private val binding: LayoutIconGridItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val title = card.header?.left?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "text"
        )?.jsonPrimitive?.content
        val link = card.header?.right?.takeIf { it.isNotEmpty() }?.get(0)?.metro_data?.get(
            "link"
        )?.jsonPrimitive?.content ?: ""
        val list = json.decodeFromJsonElement<Icons>(card.data[0].metro_data).icons
        binding.title.text = title
        binding.more.setOnClickListener {
            jump(link)
        }
        binding.more.visibility = if (link.isEmpty()) View.GONE else View.VISIBLE

        binding.iconGrid.layoutManager = GridLayoutManager(itemView.context, 3).apply {

        }
        if (binding.iconGrid.itemDecorationCount<=0){
            binding.iconGrid.addItemDecoration(GridSpaceItemDecoration(3, 10.dp))
        }
        binding.iconGrid.adapter = IconAdapter(list, jump)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): IconGridViewHolder {
            val binding = LayoutIconGridItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return IconGridViewHolder(binding, jump)
        }
    }
}

class IconAdapter(
    private val icons: List<Icon>,
    private val jump: (String) -> Unit
) : RecyclerView.Adapter<IconViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IconViewHolder = IconViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: IconViewHolder,
        position: Int
    ) {
        holder.bind(icons[position])
    }

    override fun getItemCount(): Int = icons.size
}

class IconViewHolder(
    private val binding: LayoutIconItemBinding,
    private val jump: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(icon: Icon) {
        binding.icon.load(icon.icon)
        binding.title.text = icon.name
        binding.root.setOnClickListener {
            jump(icon.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): IconViewHolder {
            val binding =
                LayoutIconItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IconViewHolder(binding, jump)
        }
    }
}