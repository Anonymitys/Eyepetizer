package com.ekko.playdetail.service

import androidx.fragment.app.FragmentActivity
import com.ekko.play.detail.databinding.ActivityPlayDetailBinding
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class ContainerViewTree @Inject constructor(activity: FragmentActivity) {

    val binding: ActivityPlayDetailBinding =
        ActivityPlayDetailBinding.inflate(activity.layoutInflater)


    init {
        activity.setContentView(binding.root)
    }
}