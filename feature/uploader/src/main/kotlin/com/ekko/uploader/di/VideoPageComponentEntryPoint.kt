package com.ekko.uploader.di

import com.ekko.uploader.anchor.VideoPageScopeAnchor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@InstallIn(VideoPageComponent::class)
interface VideoPageComponentEntryPoint {

    fun attach(): VideoPageScopeAnchor
}