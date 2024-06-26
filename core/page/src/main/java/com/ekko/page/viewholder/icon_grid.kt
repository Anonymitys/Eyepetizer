package com.ekko.page.viewholder

import android.view.LayoutInflater
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

@PagingViewHolder(CardType.ICON_GRID)
class IconGridViewHolder(
    private val binding: LayoutIconGridItemBinding,
    private val jump: (String) -> Unit
) : PageViewHolder<Icons>(binding, jump) {

    override fun bind(
        card: Icons,
        position: Int
    ) {
        binding.iconGrid.layoutManager = GridLayoutManager(itemView.context, 3)
        if (binding.iconGrid.itemDecorationCount <= 0) {
            binding.iconGrid.addItemDecoration(GridSpaceItemDecoration(3, 10.dp))
        }
        binding.iconGrid.adapter = IconAdapter(card.icons, jump)
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