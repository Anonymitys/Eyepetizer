package com.ekko.uploader.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ekko.uploader.anchor.UploadActivityAnchor
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "eyepetizer://pgc/detail/\\\\d+")
@AndroidEntryPoint
class UploaderActivity : AppCompatActivity() {

    @Inject
    lateinit var anchor: UploadActivityAnchor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            )
        )
        anchor.intentService.handleIntent(intent)
    }
}