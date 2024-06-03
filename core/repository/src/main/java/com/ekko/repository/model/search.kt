package com.ekko.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchRecommendKey(
    val item_list: List<String>? = null
)

@Serializable
data class SearchResult(
    val item_list: List<SearchResultItem>
)

@Serializable
data class SearchResultItem(
    val nav: Nav,
    val card_list: List<Card>
)

@Serializable
data class Nav(
    val title: String = "",
    val type: String = ""
)