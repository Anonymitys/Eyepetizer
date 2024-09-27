package com.ekko.playdetail.service

import android.view.View
import androidx.fragment.app.Fragment
import com.ekko.play.detail.databinding.FragmentPlayDetailBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ContainerViewTree @Inject constructor(fragment: Fragment) {

    val binding: FragmentPlayDetailBinding =
        FragmentPlayDetailBinding.inflate(fragment.layoutInflater)


    fun root(): View = binding.root
}