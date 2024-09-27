package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.model.Arguments
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.async
import javax.inject.Inject


@FragmentScoped
class RecommendRepo @Inject constructor(private val fragment: Fragment) {
    private val model: PlayDetailViewModel by fragment.viewModels()

    suspend fun load(arguments: Arguments) = fragment.lifecycleScope.async {
        model.relateRecommend(
            arguments.resourceId.toString(),
            "pgc_video"
        )
    }
}