package com.ekko.repository.model

import com.ekko.base.ktx.NumberOrStringSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 *
 * @Author Ekkoe
 * @Date 2023/9/26 16:14
 */

@Serializable
data class CardList(
    private val card_list: List<Card>? = null,
    private val item_list: List<Card>? = null,
    val item_count: Long = 0,
    @Serializable(with = NumberOrStringSerializer::class)
    val last_item_id: String = "",
) {
    val list: List<Card>?
        get() = card_list ?: item_list
}

@Serializable
data class Metro(
    val item_list: List<MetroCard<JsonObject>>? = null,
    val item_count: Long = 0,
    @Serializable(with = NumberOrStringSerializer::class)
    val last_item_id: String = "",
)

@Serializable
data class Card(
    val card_id: Long = 0,
    val type: String = "",
    val style: Style? = null,
    val interaction: Interaction? = null,
    val card_data: CardData? = null,
    val card_unique_id: String = "",
)

@Serializable
data class CardData(
    val header: Layout? = null,
    var body: MetroList? = null,
    val footer: Layout? = null,
)

@Serializable
data class Layout(
    val style: Style? = null,
    val left: List<MetroCard<JsonObject>>? = null,
    val right: List<MetroCard<JsonObject>>? = null,
    val center: List<MetroCard<JsonObject>>? = null,
    val bottom: List<MetroCard<JsonObject>>? = null,
)

@Serializable
data class Interaction(
    val scroll: String = "",
)

