package com.ekko.eyepetizer.ui.userinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ekko.eyepetizer.R
import com.ekko.repository.model.DailyCard
import com.ekko.repository.model.Item
import com.ekko.repository.model.UploaderItem

/**
 *
 * @Author Ekkoe
 * @Date 2023/2/3 18:31
 */
class UploaderWorksAdapter : PagingDataAdapter<Item, UploaderWorksViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploaderWorksViewHolder = UploaderWorksViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: UploaderWorksViewHolder,
        position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class UploaderWorksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val icon = itemView.findViewById<ImageView>(R.id.uploader_icon)
    private val name = itemView.findViewById<TextView>(R.id.uploader_name)
    private val title = itemView.findViewById<TextView>(R.id.uploader_work_title)
    private val desc = itemView.findViewById<TextView>(R.id.uploader_desc)
    private val cover = itemView.findViewById<ImageView>(R.id.uploader_video_cover)

    fun bind(item: Item) {
        Glide.with(itemView.context).load(item.data.header?.icon).transform(CircleCrop()).into(icon)
        name.text = item.data.header?.issuerName
        title.text = item.data.content?.data?.title
        desc.text = item.data.content?.data?.description
        Glide.with(itemView.context)
            .load(item.data.content?.data?.cover?.feed)
            .into(cover)
    }

    companion object {
        fun create(parent: ViewGroup): UploaderWorksViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_uploader_works, parent, false)
            return UploaderWorksViewHolder(view)
        }
    }
}