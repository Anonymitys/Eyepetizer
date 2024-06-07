package com.ekko.page.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ekko.page.databinding.DefaultItemBinding
import com.ekko.page.model.ItemCard

class DefaultViewHolder(binding: DefaultItemBinding) : PageViewHolder<String>(binding) {

    override fun bind(card: String) {
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