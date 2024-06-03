package com.ekko.page.ktx

import com.ekko.page.Scroll
import com.ekko.page.model.ItemCard
import com.ekko.repository.model.Card
import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro

fun Metro.toItemCard(): List<ItemCard> {
    return item_list?.map {
        ItemCard(
            it.style?.tpl_label ?: "",
            Scroll.VERTICAL,
            null,
            listOf(it)
        )
    } ?: emptyList()
}

fun CardList.toItemCard(): List<ItemCard> {
    return list?.filter { it.card_data?.body?.api_request == null }?.flatMap {
        val bodyList = if (it.interaction?.scroll == Scroll.HORIZONTAL) {
            listOf(
                ItemCard(
                    it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                    Scroll.HORIZONTAL,
                    it.card_data?.header,
                    it.card_data?.body?.metro_list ?: listOf()
                )
            )
        } else {
            it.card_data?.body?.metro_list?.map { metroCard ->
                ItemCard(
                    metroCard.style?.tpl_label ?: "", Scroll.VERTICAL, it.card_data?.header,
                    listOf(metroCard)
                )
            } ?: listOf()
        }
        bodyList
    } ?: emptyList()
}

fun List<Card>.toItemCard(): List<ItemCard> {
    return filter { it.card_data?.body?.api_request == null }.flatMap {
        val bodyList = if (it.interaction?.scroll == Scroll.HORIZONTAL) {
            listOf(
                ItemCard(
                    it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                    Scroll.HORIZONTAL,
                    it.card_data?.header,
                    it.card_data?.body?.metro_list ?: listOf()
                )
            )
        } else {
            it.card_data?.body?.metro_list?.map { metroCard ->
                ItemCard(
                    metroCard.style?.tpl_label ?: "", Scroll.VERTICAL, it.card_data?.header,
                    listOf(metroCard)
                )
            } ?: listOf()
        }
        bodyList
    }
}