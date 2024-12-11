package com.ekko.uploader.anchor

import com.ekko.uploader.di.VideoPageScope
import com.ekko.uploader.service.ContentService
import com.ekko.uploader.service.HeaderService
import javax.inject.Inject

@VideoPageScope
class VideoPageScopeAnchor @Inject constructor(
    headerService: HeaderService,
    contentService: ContentService
)