package com.ekko.playdetail.service

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.adapter.ContentAdapter
import com.ekko.playdetail.adapter.PlayItemCard
import com.ekko.playdetail.di.scope.VideoPageScope
import com.therouter.TheRouter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@VideoPageScope
class MainListService @Inject constructor(
    introComponent: IntroComponent,
    activity: FragmentActivity,
    recommendService: RecommendService,
    videoDetailService: VideoDetailService
) {
    private val adapter = ContentAdapter {
        TheRouter.build(it).navigation()
    }

    init {
        introComponent.binding.list.adapter = adapter
        activity.lifecycleScope.launch {
            videoDetailService.videoDetailFlow.combine(recommendService.recommendFlow) { videoDetail, recommend ->
                buildList {
                    add(PlayItemCard("content", videoDetail))
                    addAll(recommend.map { PlayItemCard("recommend", it) })
                }
            }.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}