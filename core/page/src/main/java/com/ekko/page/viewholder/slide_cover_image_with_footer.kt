package com.ekko.page.viewholder

import android.graphics.Color
import android.view.View
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.adapter.BannerAdapter
import com.ekko.page.databinding.SlideCoverWithFooterItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.SlideCover
import com.to.aboomy.pager2banner.IndicatorView

@PagingViewHolder(CardType.SLIDE_COVER_IMAGE_WITH_FOOTER)
class SlideCoverImageWithFooterViewHolder(
    private val binding: SlideCoverWithFooterItemBinding,
    private val jump: (View, String) -> Unit
) : SlideViewHolder<MetroCard<SlideCover>>(binding, jump) {

    override fun bind(card: List<MetroCard<SlideCover>>, position: Int) {
        val indicator = IndicatorView(itemView.context).setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)

        val data = card.map { it.metro_data }
        binding.banner.apply {
            isAutoPlay = false
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                (layoutParams.width - 40.dp).div(data[0].cover?.img_info?.scale ?: 1.0).toInt()
            setIndicator(indicator)
            setPageMargin(8.dp, 4.dp)
            adapter = BannerAdapter(data) {
                jump(it, data[binding.banner.currentPager].footer?.left?.link ?: "")
            }
        }
        binding.learnMore.apply {
            val item = data[binding.banner.currentPager]
            text = item.footer?.left?.text
            setOnClickListener {
                jump(it, "${item.footer?.left?.link}")
            }
        }
    }
}



