package com.ekko.playdetail.service

import com.ekko.playdetail.di.scope.VideoPageScope
import com.ekko.playdetail.pagedata.DataKey
import com.ekko.playdetail.pagedata.VideoPageData
import javax.inject.Inject

@VideoPageScope
class VideoPlayingService @Inject constructor(player: VideoPlayer, data: VideoPageData) {

    init {
        data[DataKey.VideoDetail]?.let {
            player.updateMediaSource(it)
        }
    }
}