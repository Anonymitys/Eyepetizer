package com.ekko.uploader.di

import com.ekko.repository.model.UserInfo
import com.ekko.uploader.model.IntentParameter
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import kotlinx.coroutines.CoroutineScope


@DefineComponent.Builder
interface VideoPageComponentBuilder {

    fun bindIntentParameter(@BindsInstance parameter: IntentParameter): VideoPageComponentBuilder

    fun bindVideoPageData(@BindsInstance data: UserInfo): VideoPageComponentBuilder

    fun bindCoroutineScope(@BindsInstance scope: CoroutineScope): VideoPageComponentBuilder

    fun build(): VideoPageComponent
}