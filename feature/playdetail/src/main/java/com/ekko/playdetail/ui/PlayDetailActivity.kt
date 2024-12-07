package com.ekko.playdetail.ui

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ekko.play.detail.R
import com.ekko.playdetail.di.anchor.ActivityScopedAnchor
import com.ekko.playdetail.di.component.ActivityScopedEntryPoint
import com.therouter.router.Route
import dagger.hilt.EntryPoints
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories
import dagger.hilt.android.internal.managers.ActivityComponentManager
import dagger.hilt.internal.GeneratedComponentManager
import dagger.hilt.internal.GeneratedComponentManagerHolder

@Route(path = "eyepetizer://detail/\\\\d+")
class PlayDetailActivity : AppCompatActivity(), GeneratedComponentManagerHolder {

    val componentManager: ActivityComponentManager by lazy {
        ActivityComponentManager(this)
    }

    val anchor: ActivityScopedAnchor by lazy {
        EntryPoints.get(generatedComponent(), ActivityScopedEntryPoint::class.java).attach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        anchor.intent.handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        anchor.intent.handleIntent(intent)
    }

    override fun generatedComponent(): Any = componentManager.generatedComponent()

    override fun componentManager(): GeneratedComponentManager<*> = componentManager

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = DefaultViewModelFactories.getActivityFactory(
            this,
            super.defaultViewModelProviderFactory
        )
}