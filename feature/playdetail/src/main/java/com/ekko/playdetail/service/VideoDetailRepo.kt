package com.ekko.playdetail.service

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import com.ekko.repository.model.VideoItemCard
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@ActivityScoped
class VideoDetailRepo @Inject constructor(activity: FragmentActivity) {
    private val model: PlayDetailViewModel by activity.viewModels()


    suspend fun load(arguments: Arguments): VideoItemCard {
        return model.playDetail(arguments.resourceId.toString(), "pgc_video")
    }

}