package com.ekko.playdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekko.playdetail.arguments.ArgumentsParser
import com.ekko.playdetail.di.anchor.ContainerPageScopeAnchor
import com.ekko.playdetail.di.component.ContainerPageScopeEntryPoint
import com.ekko.playdetail.di.component.PlayDetailFragmentComponentManager
import com.therouter.router.Route
import dagger.hilt.EntryPoints
import dagger.hilt.internal.GeneratedComponentManager
import dagger.hilt.internal.GeneratedComponentManagerHolder


@Route(path = "eyepetizer://detail/\\\\d+")
class PlayDetailFragment : Fragment(), GeneratedComponentManagerHolder {

    private val componentManager: PlayDetailFragmentComponentManager by lazy {
        val arguments = arguments?.let { ArgumentsParser().parse(it) }
            ?: throw IllegalArgumentException("arguments is null")
        PlayDetailFragmentComponentManager(this, arguments)
    }

    val anchor: ContainerPageScopeAnchor by lazy {
        EntryPoints.get(generatedComponent(), ContainerPageScopeEntryPoint::class.java).attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return anchor.containerViewTree.root()
    }

    override fun generatedComponent(): Any = componentManager.generatedComponent()

    override fun componentManager(): GeneratedComponentManager<*> = componentManager

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        @Suppress("UNCHECKED_CAST")
        get() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EntryPoints.get(
                    generatedComponent(),
                    ContainerPageScopeEntryPoint::class.java
                ).viewModel() as T
            }
        }
}