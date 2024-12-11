package com.ekko.uploader.service

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ekko.uploader.model.IntentParameter
import com.therouter.router.KEY_PATH
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject


@ActivityScoped
class IntentParseService @Inject constructor() {

    private var _argumentsFlow = MutableStateFlow<IntentParameter?>(null)

    val argumentFlow: Flow<IntentParameter>
        get() = _argumentsFlow.asStateFlow().filterNotNull()

    val arguments: IntentParameter
        get() = _argumentsFlow.value ?: IntentParameter()

    fun handleIntent(intent: Intent) {
        val extra = intent.extras ?: return
        val url = extra.getString(KEY_PATH) ?: ""
        val path = Uri.parse(url).path ?: ""
        val resourceId = "\\d+".toRegex().find(path)?.value?.toLong() ?: 0
        val title = Uri.decode(extra.getString(TITLE) ?: "")
        val userType = extra.getString(USER_TYPE) ?: ""
        val tabIndex = extra.getString(TAB_INDEX)
        val parameter = IntentParameter(resourceId, title, userType, tabIndex?.toIntOrNull() ?: 0)
        Log.e("huqiang", "handleIntent: $parameter")
        _argumentsFlow.value = parameter
    }

    companion object {
        const val TITLE = "title"
        const val USER_TYPE = "userType"
        const val TAB_INDEX = "tabIndex"
    }
}