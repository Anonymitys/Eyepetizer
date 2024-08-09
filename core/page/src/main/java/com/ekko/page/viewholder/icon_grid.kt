package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.recyclerview.decoration.GridSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutIconGridItemBinding
import com.ekko.page.databinding.LayoutIconItemBinding
import com.ekko.repository.model.Icon
import com.ekko.repository.model.Icons
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.ICON_GRID)
class IconGridViewHolder(
    private val binding: LayoutIconGridItemBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<Icons>>(binding, jump) {

    override fun bind(card: MetroCard<Icons>, position: Int) {
        val data = card.metro_data
        binding.iconGrid.layoutManager = GridLayoutManager(itemView.context, 3)
        if (binding.iconGrid.itemDecorationCount <= 0) {
            binding.iconGrid.addItemDecoration(GridSpaceItemDecoration(3, 10.dp))
        }
        binding.iconGrid.adapter = IconAdapter(data.icons, jump)
    }
}

class IconAdapter(
    private val icons: List<Icon>,
    private val jump: (View, String) -> Unit
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
    private val jump: (View, String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(icon: Icon) {
        binding.icon.load(icon.icon)
        binding.title.text = icon.name
        binding.root.setOnClickListener {
            jump(it, icon.link)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (View, String) -> Unit
        ): IconViewHolder {
            val binding =
                LayoutIconItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IconViewHolder(binding, jump)
        }
    }
}