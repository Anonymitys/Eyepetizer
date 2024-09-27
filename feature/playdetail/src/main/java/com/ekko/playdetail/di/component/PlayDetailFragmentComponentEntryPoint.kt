package com.ekko.playdetail.di.component

import com.ekko.playdetail.di.anchor.ContainerPageScopeAnchor
import com.ekko.playdetail.viewmodel.PlayDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn


@EntryPoint
@InstallIn(PlayDetailFragmentComponent::class)
interface ContainerPageScopeEntryPoint {

    fun viewModel():PlayDetailViewModel

    fun attach(): ContainerPageScopeAnchor
}