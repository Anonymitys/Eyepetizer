package com.ekko.page.paing3

data class PageRequest(
    val url: String,
    val params: MutableMap<String, Any>,
)