package com.ekko.uploader.di

import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent

@VideoPageScope
@DefineComponent(parent = ActivityComponent::class)
interface VideoPageComponent {

}