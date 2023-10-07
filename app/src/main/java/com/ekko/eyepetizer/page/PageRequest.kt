package com.ekko.eyepetizer.page

data class PageRequest(
    val url: String,
    val params: MutableMap<String, Any>,
)