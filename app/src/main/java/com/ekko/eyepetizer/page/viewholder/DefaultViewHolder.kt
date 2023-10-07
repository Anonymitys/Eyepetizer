package com.ekko.eyepetizer.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ekko.eyepetizer.databinding.DefaultItemBinding
import com.ekko.eyepetizer.page.ItemCard

class DefaultViewHolder(binding: DefaultItemBinding) : PageViewHolder(binding) {

    override fun bind(card: ItemCard) {
        //do nothing
    }

    companion object {
        fun create(parent: ViewGroup): DefaultViewHolder {
            val binding =
                DefaultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DefaultViewHolder(binding)
        }
    }


}