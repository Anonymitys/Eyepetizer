package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.eyepetizer.databinding.FragmentMineBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MineFragment:Fragment() {

    private lateinit var binding:FragmentMineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater)
        return binding.root
    }
}