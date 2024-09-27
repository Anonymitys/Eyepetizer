package com.ekko.playdetail.di.component

import com.ekko.playdetail.di.anchor.VideoPageScopeAnchor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@InstallIn(VideoPageComponent::class)
interface VideoPageComponentEntryPoint {

    fun attach(): VideoPageScopeAnchor
}