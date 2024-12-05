package com.ekko.playdetail.arguments

import android.net.Uri
import android.os.Bundle
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.model.Arguments
import com.therouter.router.KEY_PATH
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ArgumentsParser @Inject constructor() {

    fun parse(arguments: Bundle): Arguments {
        val url = arguments.getString(KEY_PATH) ?: ""
        val path = Uri.parse(url).path ?: ""
        val resourceId = "\\d+".toRegex().find(path)?.value?.toLong() ?: 0
        val playUrl = arguments.getString(ArgumentsKeys.PLAY_URL) ?: ""
        return Arguments(resourceId, playUrl)
    }


}