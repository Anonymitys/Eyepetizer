package com.ekko.page.viewholder

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.ekko.base.ktx.dp
import com.ekko.base.ktx.screenWidth
import com.ekko.base.recyclerview.decoration.LineSpaceItemDecoration
import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.page.CardType
import com.ekko.page.databinding.LayoutSlideUserBinding
import com.ekko.page.databinding.LayoutSlideUserItemBinding
import com.ekko.repository.model.MetroCard
import com.ekko.repository.model.Uploader

@PagingViewHolder(CardType.SLIDE_USER)
class SlideUserViewHolder(
    private val binding: LayoutSlideUserBinding,
    private val jump: (View, String) -> Unit
) : SlideViewHolder<MetroCard<Uploader>>(binding, jump) {

    override fun bind(card: List<MetroCard<Uploader>>, position: Int) {
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = UploaderAdapter(card.map { it.metro_data }, jump)
            if (itemDecorationCount <= 0) {
                addItemDecoration(LineSpaceItemDecoration(8.dp))
            }
        }
    }

}

class UploaderAdapter(private val data: List<Uploader>, private val jump: (View, String) -> Unit) :
    RecyclerView.Adapter<UploaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploaderViewHolder =
        UploaderViewHolder.create(parent, jump)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UploaderViewHolder, position: Int) {
        holder.bind(data[position])
    }

}


class UploaderViewHolder(
    private val binding: LayoutSlideUserItemBinding,
    private val jump: (View, String) -> Unit
) : ViewHolder(binding.root) {


    @SuppressLint("SetTextI18n")
    fun bind(data: Uploader) {
        binding.avatar.load(data.avatar?.url)
        binding.desc.text = data.nick
        binding.consume.text = "${data.follow_count}关注|${data.fans_count}粉丝"
    }

    companion object {
        fun create(parent: ViewGroup, jump: (View, String) -> Unit): UploaderViewHolder {
            val binding = LayoutSlideUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val width = (parent.context as? Activity)?.screenWidth ?: 1080
            binding.root.layoutParams = MarginLayoutParams(
                (width - 8.dp.times(4)).div(2.5).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 8.dp
            }
            return UploaderViewHolder(binding, jump)
        }
    }

}