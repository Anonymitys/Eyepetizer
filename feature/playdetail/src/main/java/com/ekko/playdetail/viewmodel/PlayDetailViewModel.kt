package com.ekko.playdetail.viewmodel

import androidx.lifecycle.ViewModel
import com.ekko.repository.PlayDetailRepository
import com.ekko.repository.model.RecommendCard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PlayDetailViewModel @Inject constructor(private val repository: PlayDetailRepository) :
    ViewModel() {

    suspend fun playDetail(resourceId: String, resourceType: String) =
        repository.playDetail(resourceId, resourceType)


    suspend fun relateRecommend(resourceId: String, resourceType: String): RecommendCard =
        repository.relateRecommend(resourceId, resourceType)

}