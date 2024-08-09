package com.ekko.page.model

import com.ekko.page.CardType
import com.ekko.repository.model.Layout
import com.ekko.repository.model.MetroCard
import kotlinx.serialization.json.JsonObject

sealed class ItemCard {
    abstract val uniqueId: String

    abstract val type: String
}

data class HeaderItemCard(
    override val type: String = CardType.HEADER,
    val left: Layout,
) : ItemCard() {
    override val uniqueId: String
        get() = toString()
}

data class MetroItemCard(
    override val type: String,
    val data: MetroCard<JsonObject>,
    val index: Int,
) : ItemCard() {
    override val uniqueId: String
        get() = data.metro_unique_id
}

// h-scroll
data class SlideItemCard(
    override val type: String,
    val data: List<MetroCard<JsonObject>>
) : ItemCard() {
    override val uniqueId: String
        get() = data[0].metro_unique_id
}

data class FooterItemCard(override val type: String = CardType.FOOTER) : ItemCard() {
    override val uniqueId: String
        get() = toString()
}
