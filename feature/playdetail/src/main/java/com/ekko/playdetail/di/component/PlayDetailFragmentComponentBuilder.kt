package com.ekko.playdetail.di.component

import androidx.fragment.app.Fragment
import com.ekko.playdetail.model.Arguments
import dagger.BindsInstance
import dagger.hilt.DefineComponent


@DefineComponent.Builder
interface PlayDetailFragmentComponentBuilder {

    @BindsInstance
    fun fragment(fragment: Fragment): PlayDetailFragmentComponentBuilder

    @BindsInstance
    fun arguments(arguments: Arguments): PlayDetailFragmentComponentBuilder

    fun build(): PlayDetailFragmentComponent
}