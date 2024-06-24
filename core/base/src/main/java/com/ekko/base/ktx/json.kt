package com.ekko.base.ktx

import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
}