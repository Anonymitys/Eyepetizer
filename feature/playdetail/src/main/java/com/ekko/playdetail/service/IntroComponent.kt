package com.ekko.playdetail.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.ekko.base.ktx.launchWhenCreated
import com.ekko.play.detail.databinding.FragmentPlayContentBinding
import com.ekko.playdetail.di.component.VideoPageComponentBuilder
import com.ekko.playdetail.di.component.VideoPageComponentEntryPoint
import com.ekko.playdetail.interfaces.IContent
import dagger.hilt.EntryPoints
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScoped
class IntroComponent @Inject constructor(
    private val activity: FragmentActivity,
    private val pageLoaderService: PageLoaderService,
    private val componentBuilder: VideoPageComponentBuilder
) : IContent {
    lateinit var binding: FragmentPlayContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       activity.lifecycleScope.launch {
            pageLoaderService
                .uiState
                .collectLatest {
                    binding.progress.isVisible = it is UiState.Loading
                    binding.errorView.isVisible = it is UiState.Error
                    when (it) {
                        is UiState.Error -> {
                            binding.errorView
                                .bindErrorMsg(it.error.message.toString()) {
                                    pageLoaderService.reLoad()
                                }
                        }

                        is UiState.Success -> {
                            val graph = componentBuilder.bindVideoPageData(it.data).build()
                            EntryPoints.get(graph, VideoPageComponentEntryPoint::class.java)
                                .attach()
                        }

                        is UiState.Loading -> {
                            //
                        }
                    }


                }
        }
    }
}