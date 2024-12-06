package com.ekko.base.navigator

import com.therouter.TheRouter


fun navigateTo(url: String) {
    TheRouter.build(url).navigation()
}