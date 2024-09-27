package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import com.ekko.repository.model.VideoItemCard
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class VideoDetailRepo @Inject constructor(fragment: Fragment) {
    private val model: PlayDetailViewModel by fragment.viewModels()


    suspend fun load(arguments: Arguments): VideoItemCard {
        return model.playDetail(arguments.resourceId.toString(), "pgc_video")
    }

}