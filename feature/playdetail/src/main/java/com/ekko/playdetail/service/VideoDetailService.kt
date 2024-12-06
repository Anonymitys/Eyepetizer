package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import com.ekko.playdetail.di.scope.VideoPageScope
import com.ekko.playdetail.pagedata.DataKey
import com.ekko.playdetail.pagedata.VideoPageData
import com.ekko.repository.model.VideoItemCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@VideoPageScope
class VideoDetailService @Inject constructor(
    videoPageData: VideoPageData
) {
    private val _videoDetailFlow = MutableStateFlow<VideoItemCard?>(null)

    val videoDetailFlow
        get() = _videoDetailFlow.asStateFlow().filterNotNull()

    init {
        _videoDetailFlow.value = videoPageData[DataKey.VideoDetail]
    }
}