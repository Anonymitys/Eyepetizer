package com.ekko.playdetail.service

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ekko.playdetail.di.scope.VideoPageScope
import com.ekko.playdetail.pagedata.DataKey
import com.ekko.playdetail.pagedata.VideoPageData
import com.ekko.repository.model.VideoItemCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@VideoPageScope
class RecommendService @Inject constructor(fragment: Fragment, videoPageData: VideoPageData) {

    private val _recommendFlow = MutableStateFlow<List<VideoItemCard>>(emptyList())

    val recommendFlow
        get() = _recommendFlow.asStateFlow().filterNotNull()

    init {
        fragment.lifecycleScope.launch {
            val recommendCard = videoPageData[DataKey.Recommend]?.await()
            _recommendFlow.emit(recommendCard?.item_list ?: emptyList())
        }
    }
}