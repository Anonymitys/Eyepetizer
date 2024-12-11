package com.ekko.uploader.service

import android.graphics.Color
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ekko.base.ktx.isDark
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityScoped
class ToolBarService @Inject constructor(
    private val activity: FragmentActivity,
    private val containerTree: ContainerTree,
    appLayoutConfigureService: AppLayoutConfigureService,
    private val statusBarService: StatusBarService,
    private val intentParseService: IntentParseService
) {

    private val toolBar: MaterialToolbar
        get() = containerTree.binding.toolBar

    private val root: View
        get() = containerTree.binding.root

    private var insetsTop = 0

    init {
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            insetsTop.takeIf { it <= 0 }?.let {
                toolBar.updateParams(inset)
                insetsTop = inset.top
            }
            insets
        }

        toolBar.setOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

        activity.lifecycleScope.launch {
            appLayoutConfigureService.collapseState.collectLatest {
                toolBar.title = if (it) intentParseService.arguments.title else ""
                navigationIconTint(it)
                if (activity.isDark.not()) {
                    statusBarService.lightTheme(it)
                }
            }
        }

    }

    private fun MaterialToolbar.updateParams(insets: Insets) {
        updatePadding(0, insets.top, 0, 0)
        updateLayoutParams {
            height += insets.top
        }
    }

    private fun navigationIconTint(collapse: Boolean) {
        val color = if (activity.isDark) Color.WHITE
        else if (collapse) Color.BLACK
        else Color.WHITE
        toolBar.setNavigationIconTint(color)
    }
}