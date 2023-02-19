package com.ekko.eyepetizer.ui.home.daily

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ekko.eyepetizer.R
import com.ekko.eyepetizer.ui.userinfo.UploaderActivity
import com.ekko.repository.model.Item

/**
 *
 * @Author Ekkoe
 * @Date 2023/1/31 12:46
 */
class DailyAdapter : PagingDataAdapter<Item, DailyViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyViewHolder {
        return when (viewType) {
            HEADER -> DailyHeaderViewHolder.create(parent)
            CONTENT -> DailyContentViewHolder.create(parent)
            else -> throw RuntimeException("no match ViewHolder")
        }
    }

    override fun onBindViewHolder(
        holder: DailyViewHolder,
        position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item?.type == TEXT_CARD) return HEADER
        return CONTENT
    }

    companion object {
        val COMPARATOR = object : ItemCallback<Item>() {
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

        const val TEXT_CARD = "textCard"
        const val HEADER = 1
        const val CONTENT = 2
    }
}

abstract class DailyViewHolder(view: View) : ViewHolder(view) {
    abstract fun bind(item: Item)
}

class DailyHeaderViewHolder(view: View) : DailyViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.header_title)
    override fun bind(item: Item) {
        title.text = item.data.text
    }

    companion object {
        fun create(parent: ViewGroup): DailyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_header, parent, false
            )
            return DailyHeaderViewHolder(view)
        }
    }
}

class DailyContentViewHolder(view: View) : DailyViewHolder(view) {
    private val cover = view.findViewById<ImageView>(R.id.daily_cover)
    private val icon = view.findViewById<ImageView>(R.id.daily_icon)
    private val title = view.findViewById<TextView>(R.id.daily_title)
    private val desc = view.findViewById<TextView>(R.id.daily_desc)
    private val time = view.findViewById<TextView>(R.id.daily_duration)

    init {
        (itemView.context as? Activity)?.window?.decorView?.let {
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            params.width = it.width
            params.height = it.width * 9 / 16
            cover.layoutParams = params
        }
        icon.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context,UploaderActivity::class.java))
        }
    }

    override fun bind(item: Item) {
        item.data.apply {
            Glide.with(itemView.context).load(content?.data?.cover?.feed).into(cover)
            Glide.with(itemView.context).load(header?.icon).transform(CircleCrop()).into(icon)
            title.text = header?.title
            desc.text = header?.description
            time.text = formatTime(content?.data?.duration ?: 0)
        }
    }

    private fun formatTime(duration: Int): String {
        val hours = duration / 3600
        val rem = duration % 3600
        val minutes = rem / 60
        val seconds = rem % 60
        return if (hours <= 0) {
            String.format("%02d:%02d", minutes, seconds)
        } else {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }

    companion object {
        fun create(parent: ViewGroup): DailyContentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_content, parent, false
            )
            return DailyContentViewHolder(view)
        }
    }
}