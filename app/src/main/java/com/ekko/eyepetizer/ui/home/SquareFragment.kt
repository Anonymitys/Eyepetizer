package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.eyepetizer.R
import com.ekko.eyepetizer.databinding.FragmentSquareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareFragment : Fragment() {

    private lateinit var binding: FragmentSquareBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSquareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.square_container, SquareContentFragment())
            .commitAllowingStateLoss()
    }

}