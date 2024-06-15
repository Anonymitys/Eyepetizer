package com.ekko.page.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ekko.repository.model.SlideCover

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
) : RecyclerView.ViewHolder(view) {

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
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                        )
                }
            return BannerViewHolder(imageView, jump)
        }
    }
}