package com.ekko.uploader.anchor


import com.ekko.uploader.service.ContainerTree
import com.ekko.uploader.service.IntentParseService
import com.ekko.uploader.service.LoadingViewService
import com.ekko.uploader.service.PageLoadService
import com.ekko.uploader.service.ToolBarService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class UploadActivityAnchor @Inject constructor(
    val intentService: IntentParseService,
    containerTree: ContainerTree,
    toolBarService: ToolBarService,
    pageLoadService: PageLoadService,
    loadingViewService: LoadingViewService
)