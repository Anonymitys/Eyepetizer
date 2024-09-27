package com.ekko.playdetail.di.component

import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.FragmentScoped


@FragmentScoped
@DefineComponent(parent = ActivityComponent::class)
interface PlayDetailFragmentComponent {
}