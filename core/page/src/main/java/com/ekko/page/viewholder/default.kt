package com.ekko.page.viewholder

import android.view.View
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.DefaultItemBinding

@PagingViewHolder(CardType.DEFAULT)
class DefaultViewHolder(
    binding: DefaultItemBinding,
    val jump: (View,String) -> Unit
) : PageViewHolder<String>(binding) {

    override fun bind(
        card: String,
        position: Int
    ) {
        //do nothing
    }
}