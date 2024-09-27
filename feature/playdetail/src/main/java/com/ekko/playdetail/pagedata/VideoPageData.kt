package com.ekko.playdetail.pagedata

import com.ekko.repository.model.RecommendCard
import com.ekko.repository.model.VideoItemCard
import kotlinx.coroutines.Deferred

class VideoPageData {

    private val map = mutableMapOf<DataKey<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: DataKey<T>): T? {
        return map[key] as? T
    }

    operator fun <T> set(key: DataKey<T>, value: T) {
        map[key] = value as Any
    }
}


sealed class DataKey<T> {
    data object VideoDetail : DataKey<VideoItemCard>()
    data object Recommend : DataKey<Deferred<RecommendCard>>()
}