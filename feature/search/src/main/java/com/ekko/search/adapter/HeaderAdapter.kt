package com.ekko.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekko.repository.model.CardHeader
import com.ekko.search.databinding.LayoutHeaderItemBinding

class HeaderAdapter(private val header: CardHeader) : Adapter<HeaderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderViewHolder = HeaderViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: HeaderViewHolder,
        position: Int
    ) {
        holder.bind(header)
    }

    override fun getItemCount(): Int = 1
}

class HeaderViewHolder(private val binding: LayoutHeaderItemBinding) : ViewHolder(binding.root) {

    fun bind(header: CardHeader) {
        binding.title.text = header.text
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val binding =
                LayoutHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(binding)
        }
    }
}