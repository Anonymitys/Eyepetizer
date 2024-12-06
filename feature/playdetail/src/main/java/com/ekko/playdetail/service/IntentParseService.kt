package com.ekko.playdetail.service

import android.content.Intent
import android.net.Uri
import com.ekko.playdetail.model.Arguments
import com.therouter.router.KEY_PATH
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@ActivityScoped
class IntentParseService @Inject constructor(private val intentParameters: IntentParameters) {
    private var _argumentsFlow = MutableStateFlow<Arguments?>(null)

    val argumentFlow: Flow<Arguments>
        get() = _argumentsFlow.asStateFlow().filterNotNull()

    fun handleIntent(intent: Intent) {
        val extra = intent.extras ?: return
        val url = extra.getString(KEY_PATH) ?: ""
        val path = Uri.parse(url).path ?: ""
        val resourceId = "\\d+".toRegex().find(path)?.value?.toLong() ?: 0
        val argument = Arguments(resourceId, "")
        intentParameters.inject(argument)
        _argumentsFlow.value = argument
    }

}