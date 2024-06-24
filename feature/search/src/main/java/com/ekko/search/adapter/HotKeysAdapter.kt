package com.ekko.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekko.base.ktx.dp
import com.ekko.search.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class HotKeysAdapter(
    private val data: List<String>,
    private val click: (String) -> Unit
) : RecyclerView.Adapter<HotKeyViewHolder>() {

    override fun getItemCount(): Int = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotKeyViewHolder = HotKeyViewHolder.create(parent, click)

    override fun onBindViewHolder(
        holder: HotKeyViewHolder,
        position: Int
    ) {
        holder.bind(data)
    }
}

class HotKeyViewHolder(
    private val chipGroup: ChipGroup,
    private val click: (String) -> Unit
) : ViewHolder(chipGroup) {

    fun bind(data: List<String>) {

        chipGroup.removeAllViews()
        data.forEach {
            val chip = LayoutInflater.from(itemView.context)
                .inflate(R.layout.chip_group_item_assist, chipGroup, false) as Chip
            chip.text = it
            chip.setOnClickListener { _ ->
                click(it)
            }
            chipGroup.addView(chip)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            click: (String) -> Unit
        ): HotKeyViewHolder {
            return HotKeyViewHolder(ChipGroup(parent.context).apply {
                chipSpacingVertical = 0
                setPadding(16.dp, 0, 16.dp, 8.dp)
            }, click)
        }
    }
}