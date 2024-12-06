package com.ekko.playdetail.di.anchor

import com.ekko.playdetail.service.ConfigurationService
import com.ekko.playdetail.service.ContainerViewTree
import com.ekko.playdetail.service.ContentService
import com.ekko.playdetail.service.IntentParseService
import com.ekko.playdetail.service.ScrollModeService
import com.ekko.playdetail.service.TabService
import com.ekko.playdetail.service.ToolbarService
import com.ekko.playdetail.service.VideoPlayer
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class ActivityScopedAnchor @Inject constructor(
    val intent:IntentParseService,
    val containerViewTree: ContainerViewTree,
    val videoPlayer: VideoPlayer,
    val toolbarService: ToolbarService,
    val tabService: TabService,
    val contentService: ContentService,
    val scrollModeService: ScrollModeService,
    val configurationService: ConfigurationService
)