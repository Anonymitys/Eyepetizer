package com.ekko.page.model

import com.ekko.repository.model.Layout
import com.ekko.repository.model.MetroCard

data class ItemCard(
    val type: String,
    val scroll: String,
    val header: Layout?,
    val data: List<MetroCard>,
)
