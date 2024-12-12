package com.ekko.page.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutDescriptionTextBinding
import com.ekko.repository.model.DescriptionText
import com.ekko.repository.model.MetroCard

@PagingViewHolder(CardType.DESCRIPTION_TEXT)
class DescriptionTextViewHolder(
    private val binding: LayoutDescriptionTextBinding,
    private val jump: (View, String) -> Unit
) : PageViewHolder<MetroCard<DescriptionText>>(binding, jump) {

    override fun bind(card: MetroCard<DescriptionText>, position: Int) {
        binding.desc.text = card.metro_data.text
        val isVisible = card.metro_data.text.isNotEmpty()
        binding.desc.isVisible = isVisible
    }

}