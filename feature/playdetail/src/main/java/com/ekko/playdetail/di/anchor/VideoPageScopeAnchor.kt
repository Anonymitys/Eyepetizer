package com.ekko.playdetail.di.anchor

import com.ekko.playdetail.di.scope.VideoPageScope
import com.ekko.playdetail.service.MainListService
import com.ekko.playdetail.service.VideoPlayingService
import javax.inject.Inject

@VideoPageScope
class VideoPageScopeAnchor @Inject constructor(
    mainListService: MainListService,
    videoPlayingService: VideoPlayingService
)