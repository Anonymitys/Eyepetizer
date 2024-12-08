package com.ekko.playdetail.service

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            Log.e("huqiang", ": systemBars:$systemBars", )
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
    }
}