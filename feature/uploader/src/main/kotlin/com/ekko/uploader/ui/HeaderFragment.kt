package com.ekko.uploader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.ekko.uploader.databinding.FragmentHeaderBinding

class HeaderFragment : Fragment() {
    private lateinit var binding: FragmentHeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.background.load("http://ali-img.kaiyanapp.com/f9a3fddd3f0941404f4b1d30235c2952.png?imageMogr2/quality/60/format/jpg")
        binding.cover.load("http://ali-img.kaiyanapp.com/5f0f992e79639d621aec91fe760a4836.jpeg?image_process=image/auto-orient,1/resize,w_360/format,webp/interlace,1/quality,q_80")
        binding.name.text = "影视杂谈"
        binding.desc.text = "一个影视相关内容的大杂谈！"
    }
}