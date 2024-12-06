package com.ekko.playdetail.di.component

import com.ekko.playdetail.di.scope.VideoPageScope
import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent

@VideoPageScope
@DefineComponent(parent = ActivityComponent::class)
interface VideoPageComponent {

}