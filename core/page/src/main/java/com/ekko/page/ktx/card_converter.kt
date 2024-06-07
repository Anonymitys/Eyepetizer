package com.ekko.page.ktx

import com.ekko.page.Scroll
import com.ekko.page.model.FooterItemCard
import com.ekko.page.model.HeaderItemCard
import com.ekko.page.model.ItemCard
import com.ekko.page.model.MetroItemCard
import com.ekko.page.model.SlideItemCard
import com.ekko.repository.model.Card
import com.ekko.repository.model.CardList
import com.ekko.repository.model.Metro

fun Metro.toItemCard(): List<ItemCard> {
    return item_list?.map {
        MetroItemCard(
            it.style?.tpl_label ?: "",
            it
        )
    } ?: emptyList()
}

fun CardList.toItemCard(): List<ItemCard> {
    return list?.filter { it.card_data?.body?.api_request == null }?.flatMap {
        val list = mutableListOf<ItemCard>()
        if (!it.card_data?.header?.left.isNullOrEmpty()) {
            list.add(HeaderItemCard(left = it.card_data?.header!!))
        }
        if (it.interaction?.scroll == Scroll.HORIZONTAL) {
            list.add(
                SlideItemCard(
                    it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                    it.card_data?.body?.metro_list ?: listOf()
                )
            )
        } else {
            list.addAll(it.card_data?.body?.metro_list?.map { metroCard ->
                MetroItemCard(
                    metroCard.style?.tpl_label ?: "", metroCard,
                )
            } ?: listOf())
        }
        list
    } ?: emptyList()
}

fun List<Card>.toItemCard(): List<ItemCard> {
    return filter { it.card_data?.body?.api_request == null }.flatMap {
        val list = mutableListOf<ItemCard>()
        if (!it.card_data?.header?.left.isNullOrEmpty()) {
            list.add(HeaderItemCard(left = it.card_data?.header!!))
        }
        if (it.interaction?.scroll == Scroll.HORIZONTAL) {
            list.add(
                SlideItemCard(
                    it.card_data?.body?.metro_list?.get(0)?.style?.tpl_label ?: "",
                    it.card_data?.body?.metro_list ?: listOf()
                )
            )
        } else {
            list.addAll(it.card_data?.body?.metro_list?.map { metroCard ->
                MetroItemCard(
                    metroCard.style?.tpl_label ?: "", metroCard,
                )
            } ?: listOf())
        }
        list
    }
}