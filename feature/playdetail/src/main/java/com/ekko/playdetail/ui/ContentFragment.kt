package com.ekko.playdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekko.playdetail.constants.ArgumentsKeys

class ContentFragment : Fragment() {

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
        // 不要直接注入contentService，会导致不是同一个实例
        return (parentFragment as PlayDetailFragment).anchor.contentService.onCreateView(
            tabType,
            inflater,
            container,
            savedInstanceState
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (parentFragment as PlayDetailFragment).anchor.contentService.onViewCreated(
            tabType,
            view,
            savedInstanceState
        )
    }
}