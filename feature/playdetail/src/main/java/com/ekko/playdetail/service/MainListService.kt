package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ekko.base.navigator.navigateTo
import com.ekko.playdetail.adapter.ContentAdapter
import com.ekko.playdetail.adapter.PlayItemCard
import com.ekko.playdetail.di.scope.VideoPageScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@VideoPageScope
class MainListService @Inject constructor(
    introComponent: IntroComponent,
    fragment: Fragment,
    recommendService: RecommendService,
    videoDetailService: VideoDetailService
) {
    private val adapter = ContentAdapter {
        fragment.navigateTo(it)
    }

    init {
        introComponent.binding.list.adapter = adapter
        fragment.lifecycleScope.launch {
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