package com.ekko.playdetail.di.anchor

import com.ekko.playdetail.service.ConfigurationService
import com.ekko.playdetail.service.ContainerViewTree
import com.ekko.playdetail.service.ContentService
import com.ekko.playdetail.service.ScrollModeService
import com.ekko.playdetail.service.StatusBarService
import com.ekko.playdetail.service.TabService
import com.ekko.playdetail.service.ToolbarService
import com.ekko.playdetail.service.VideoPlayer
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ContainerPageScopeAnchor @Inject constructor(
    val containerViewTree: ContainerViewTree,
    val videoPlayer: VideoPlayer,
    val toolbarService: ToolbarService,
    val statusBarService: StatusBarService,
    val tabService: TabService,
    val contentService: ContentService,
    val scrollModeService: ScrollModeService,
    val configurationService: ConfigurationService
)