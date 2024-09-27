package com.ekko.playdetail.arguments

import android.os.Bundle
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.model.Arguments
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ArgumentsParser @Inject constructor() {

    fun parse(arguments: Bundle): Arguments {
        val resourceId = arguments.getLong(ArgumentsKeys.RESOURCE_ID)
        val playUrl = arguments.getString(ArgumentsKeys.PLAY_URL) ?: ""
        return Arguments(resourceId, playUrl)
    }
}