package com.ekko.eyepetizer.page.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.ekko.base.dp
import com.ekko.base.json
import com.ekko.base.screenWidth
import com.ekko.eyepetizer.databinding.SlideCoverWithFooterItemBinding
import com.ekko.eyepetizer.page.ItemCard
import com.ekko.repository.model.SlideCover
import com.to.aboomy.pager2banner.IndicatorView
import kotlinx.serialization.json.decodeFromJsonElement

class SlideCoverWithFooterViewHolder(
    private val binding: SlideCoverWithFooterItemBinding,
    private val jump: (String) -> Unit
) :
    PageViewHolder(binding, jump) {

    override fun bind(card: ItemCard) {
        val list = card.data.map { json.decodeFromJsonElement<SlideCover>(it.metro_data) }
        val indicator = IndicatorView(itemView.context)
            .setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)

        binding.banner.apply {
            isAutoPlay = false
            layoutParams.width = itemView.context.screenWidth
            layoutParams.height =
                (layoutParams.width - 40.dp).div(list[0].cover?.img_info?.scale ?: 1.0).toInt()
            setIndicator(indicator)
            setPageMargin(15.dp, 5.dp)
            adapter = BannerAdapter(list) {
                jump(list[binding.banner.currentPager].footer?.left?.link ?: "")
            }
        }
        binding.learnMore.apply {
            val item = list[binding.banner.currentPager]
            text = item.footer?.left?.text
            setOnClickListener {
                jump("${item.footer?.left?.link}")
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: (String) -> Unit
        ): SlideCoverWithFooterViewHolder {
            val binding =
                SlideCoverWithFooterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return SlideCoverWithFooterViewHolder(binding, jump)
        }
    }
}

class BannerAdapter(
    private val list: List<SlideCover>,
    private val jump: () -> Unit
) :
    RecyclerView.Adapter<BannerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder =
        BannerViewHolder.create(parent, jump)

    override fun onBindViewHolder(
        holder: BannerViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class BannerViewHolder(
    private val view: ImageView,
    jump: () -> Unit
) : ViewHolder(view) {

    init {
        itemView.setOnClickListener { jump() }
    }

    fun bind(cover: SlideCover) {
        view.load(cover.cover?.url) {
            crossfade(true)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            jump: () -> Unit
        ): BannerViewHolder {
            val imageView =
                ImageView(parent.context).also {
                    it.scaleType = ImageView.ScaleType.CENTER_CROP
                    it.layoutParams =
                        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                }
            return BannerViewHolder(imageView, jump)
        }
    }
}

