package com.ekko.uploader.service

import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@ActivityScoped
class AppLayoutConfigureService @Inject constructor(
    containerVIewTree: ContainerTree,
) {
    private val appBar = containerVIewTree.binding.appBar
    private val collapsingToolbarLayout = containerVIewTree.binding.collapseLayout

    private val _collapseState = MutableStateFlow(false)
    val collapseState
        get() = _collapseState

    private val currentOffsetFlow = MutableStateFlow(0)


    init {
        var isCollapse = false
        appBar.addOnOffsetChangedListener { _, verticalOffset ->
            currentOffsetFlow.value = verticalOffset
            val collapse =
                collapsingToolbarLayout.height + verticalOffset < collapsingToolbarLayout.scrimVisibleHeightTrigger
            if (isCollapse == collapse) return@addOnOffsetChangedListener
            isCollapse = collapse
            _collapseState.value = collapse
        }
    }


    fun addOnOffsetChangedListener(listener: AppBarLayout.OnOffsetChangedListener) {
        appBar.addOnOffsetChangedListener(listener)
    }

    fun removeOnOffsetChangedListener(listener: AppBarLayout.OnOffsetChangedListener) {
        appBar.removeOnOffsetChangedListener(listener)
    }

    fun setExpanded(expand: Boolean) {
        appBar.setExpanded(expand)
    }

    suspend fun awaitCompleteExpand() {
        currentOffsetFlow.first { it == 0 }
    }
}