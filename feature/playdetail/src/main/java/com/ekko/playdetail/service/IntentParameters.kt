package com.ekko.playdetail.service

import com.ekko.playdetail.model.Arguments
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class IntentParameters @Inject constructor() {

    private var _argument: Arguments = Arguments(0, "")

    val arguments: Arguments
        get() = _argument

    val resourceId: Long
        get() = _argument.resourceId

    fun inject(parameters: Arguments) {
        _argument = parameters
    }

}