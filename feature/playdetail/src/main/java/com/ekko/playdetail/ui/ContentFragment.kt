package com.ekko.playdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.playdetail.constants.ArgumentsKeys
import com.ekko.playdetail.service.ContentService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContentFragment : Fragment() {

    @Inject
    lateinit var contentService: ContentService

    private lateinit var tabType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = arguments?.getString(ArgumentsKeys.TAB_TYPE) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return contentService.onCreateView(
            tabType,
            inflater,
            container,
            savedInstanceState
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentService.onViewCreated(
            tabType,
            view,
            savedInstanceState
        )
    }
}