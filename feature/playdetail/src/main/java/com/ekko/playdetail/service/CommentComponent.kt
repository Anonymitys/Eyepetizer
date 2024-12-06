package com.ekko.playdetail.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekko.play.detail.databinding.FragmentPlayCommentBinding
import com.ekko.playdetail.interfaces.IContent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@ActivityScoped
class CommentComponent @Inject constructor() : IContent {
    private lateinit var binding: FragmentPlayCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
    }
}