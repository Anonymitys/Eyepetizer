package com.ekko.repository.model


import kotlinx.serialization.Serializable

@Serializable
data class NavResponse(
    val nav_item: NavItem = NavItem(),
    val style: String = ""
)

@Serializable
data class NavItem(
    val left: List<NavItemLeft> = emptyList(),
)

@Serializable
data class NavItemLeft(
    val label: String = "",
    val text: String = "",
    val type: String = ""
)