package com.ekko.playdetail.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekko.playdetail.constants.Type
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ContentService @Inject constructor(
    private val introService: IntroComponent,
    private val commentService: CommentComponent
) {

    fun onCreateView(
        type: String,
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return when (type) {
            Type.INTRO -> introService.onCreateView(inflater, container, savedInstanceState)
            Type.COMMENT -> commentService.onCreateView(inflater, container, savedInstanceState)
            else -> {
                throw IllegalArgumentException("Unknown type: $type")
            }
        }
    }

    fun onViewCreated(type: String, view: View, savedInstanceState: Bundle?) {
        when (type) {
            Type.INTRO -> introService.onViewCreated(view, savedInstanceState)
            Type.COMMENT -> commentService.onViewCreated(view, savedInstanceState)
            else -> {
                throw IllegalArgumentException("Unknown type: $type")
            }
        }
    }
}