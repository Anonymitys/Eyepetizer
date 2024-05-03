package com.ekko.api.core.constant

import java.util.UUID

const val APP_ID = "ahpagrcrf2p7m6rg"
const val X_API_KEY = "0530ee4341324ce2b26c23fcece80ea2"
val SESSION_ID by lazy { UUID.randomUUID().toString().replace("-", "") }