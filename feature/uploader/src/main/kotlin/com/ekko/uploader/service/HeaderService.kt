package com.ekko.uploader.service

import com.ekko.repository.model.UserInfo
import com.ekko.uploader.di.VideoPageScope
import com.ekko.uploader.view.HeaderView
import javax.inject.Inject

@VideoPageScope
class HeaderService @Inject constructor(
    private val containerTree: ContainerTree,
    private val userInfo: UserInfo
) {

    private val headerView: HeaderView
        get() = containerTree.binding.headerView

    init {
        headerView.updateHeader(userInfo)
    }

}