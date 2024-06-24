package com.ekko.base.ktx

import android.content.res.Resources

val Int.dp: Int
    get() {
        return (0.5 + this.times(Resources.getSystem().displayMetrics.density)).toInt()
    }