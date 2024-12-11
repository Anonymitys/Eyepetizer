package com.ekko.uploader.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import coil.load
import com.ekko.repository.model.UserInfo
import com.ekko.uploader.R
import com.ekko.uploader.databinding.HeaderViewBinding

class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = HeaderViewBinding.inflate(LayoutInflater.from(context), this, true)


    @SuppressLint("SetTextI18n")
    fun updateHeader(userInfo: UserInfo) {
        binding.background.load(userInfo.cover){
            placeholder(R.drawable.uploader_background)
        }
        binding.cover.load(userInfo.avatar.url)
        binding.name.text = userInfo.nick
        binding.fans.text = userInfo.fans_count.toString()
        binding.follow.text = userInfo.follow_count.toString()
        binding.media.text = userInfo.medal_count.toString()
        binding.desc.text = userInfo.description
        binding.brife.text = userInfo.brief
        binding.location.text = "IP属地：${userInfo.location}"
    }
}