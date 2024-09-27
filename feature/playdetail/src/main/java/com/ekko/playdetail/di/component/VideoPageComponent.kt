package com.ekko.playdetail.di.component

import com.ekko.playdetail.di.scope.VideoPageScope
import dagger.hilt.DefineComponent

@VideoPageScope
@DefineComponent(parent = PlayDetailFragmentComponent::class)
interface VideoPageComponent {

}