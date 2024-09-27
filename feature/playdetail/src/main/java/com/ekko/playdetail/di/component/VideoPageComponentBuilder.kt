package com.ekko.playdetail.di.component

import com.ekko.playdetail.pagedata.VideoPageData
import dagger.BindsInstance
import dagger.hilt.DefineComponent


@DefineComponent.Builder
interface VideoPageComponentBuilder {

    fun bindVideoPageData(@BindsInstance data:VideoPageData):VideoPageComponentBuilder

    fun build():VideoPageComponent
}