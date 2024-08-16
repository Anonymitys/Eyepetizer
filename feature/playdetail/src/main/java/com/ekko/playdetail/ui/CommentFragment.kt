package com.ekko.playdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.play.detail.databinding.FragmentPlayCommentBinding

class CommentFragment : Fragment() {

    private lateinit var binding: FragmentPlayCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayCommentBinding.inflate(inflater, container, false)
        return binding.root
    }
}