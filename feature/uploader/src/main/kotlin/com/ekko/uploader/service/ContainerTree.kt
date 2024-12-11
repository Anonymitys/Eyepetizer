package com.ekko.uploader.service

import androidx.fragment.app.FragmentActivity
import com.ekko.uploader.databinding.ActivityUploaderBinding
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class ContainerTree @Inject constructor(activity: FragmentActivity) {

    val binding: ActivityUploaderBinding =
        ActivityUploaderBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }
}