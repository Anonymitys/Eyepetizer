package com.ekko.base

import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}