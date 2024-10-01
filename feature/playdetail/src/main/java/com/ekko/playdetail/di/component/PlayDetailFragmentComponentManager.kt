package com.ekko.playdetail.di.component

import androidx.fragment.app.Fragment
import com.ekko.playdetail.model.Arguments
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.internal.GeneratedComponentManager

class PlayDetailFragmentComponentManager(fragment: Fragment, arguments: Arguments) :
    GeneratedComponentManager<PlayDetailFragmentComponent> {

    private val component by lazy {
        EntryPoints.get(fragment.requireHost(), PlayDetailFragmentComponentEntryPoint::class.java)
            .componentBuilder().fragment(fragment).context(fragment.requireContext())
            .arguments(arguments).build()
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface PlayDetailFragmentComponentEntryPoint {
        fun componentBuilder(): PlayDetailFragmentComponentBuilder
    }


    override fun generatedComponent(): PlayDetailFragmentComponent = component

}