package com.ekko.playdetail.di.component

import com.ekko.playdetail.di.anchor.ActivityScopedAnchor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ActivityScopedEntryPoint {

    fun attach(): ActivityScopedAnchor
}