package com.ekko.page.viewholder

import android.graphics.Color
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.ekko.base.dp
import com.ekko.base.screenWidth
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.adapter.BannerAdapter
import com.ekko.page.databinding.SlideCoverWithFooterItemBinding
import com.ekko.repository.model.SlideCover
import com.to.aboomy.pager2banner.IndicatorView

@PagingViewHolder(CardType.SLIDE_COVER_IMAGE_WITH_FOOTER)
class SlideCoverImageWithFooterViewHolder(
    private val binding: SlideCoverWithFooterItemBinding,
    private val jump: (String) -> Unit
) : SlideViewHolder<SlideCover>(binding, jump) {

    override fun bind(card: List<SlideCover>) {
        val indicator = IndicatorView(itemView.context).setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)

        binding.banner.apply {
            isAutoPlay = false
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                (layoutParams.width - 40.dp).div(card[0].cover?.img_info?.scale ?: 1.0).toInt()
            setIndicator(indicator)
            setPageMargin(15.dp, 5.dp)
            adapter = BannerAdapter(card) {
                jump(card[binding.banner.currentPager].footer?.left?.link ?: "")
            }
        }
        binding.learnMore.apply {
            val item = card[binding.banner.currentPager]
            text = item.footer?.left?.text
            setOnClickListener {
                jump("${item.footer?.left?.link}")
            }
        }
    }
}



