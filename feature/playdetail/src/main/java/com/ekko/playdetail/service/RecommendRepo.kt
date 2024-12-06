package com.ekko.playdetail.service

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.async
import javax.inject.Inject


@ActivityScoped
class RecommendRepo @Inject constructor(private val activity:FragmentActivity) {
    private val model: PlayDetailViewModel by activity.viewModels()

    suspend fun load(arguments: Arguments) = activity.lifecycleScope.async {
        model.relateRecommend(
            arguments.resourceId.toString(),
            "pgc_video"
        )
    }
}