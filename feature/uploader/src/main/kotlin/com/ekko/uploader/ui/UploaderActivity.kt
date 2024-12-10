package com.ekko.uploader.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekko.uploader.databinding.ActivityUploaderBinding
import com.ekko.uploader.viewmodel.UIState
import com.ekko.uploader.viewmodel.UploaderViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Route(path = "eyepetizer://pgc/detail/\\\\d+")
@AndroidEntryPoint
class UploaderActivity : AppCompatActivity() {
    private val model by viewModels<UploaderViewModel>()

    private val windowInsetsController by lazy {
        WindowCompat.getInsetsController(
            window, window.decorView
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            )
        )
        val binding = ActivityUploaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            binding.toolBar.updateParams(inset)
            insets
        }
        lifecycleScope.launch {
            model.load(301637001)
        }

        lifecycleScope.launch {
            model.uiState.collectLatest {
                when (it) {
                    is UIState.Loading -> {

                    }

                    is UIState.Success -> {
                        val data = it.data
                        Log.e("huqiang", "UIState.Success: $data")
                    }

                    is UIState.Error -> {
                        Log.e("huqiang", "UIState.Error: ${it.message}")
                    }
                }
            }
        }
        binding.toolBar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.list.adapter = StringAdapter(data())
    }

    fun data(): List<String> {
        val data = mutableListOf<String>()
        repeat(100) {
            data.add("hello world $it")
        }
        return data
    }


    private fun ActivityUploaderBinding.setUpViewPager() {

    }

    private fun MaterialToolbar.updateParams(insets: Insets) {
        updatePadding(0, insets.top, 0, 0)
        updateLayoutParams {
            height += insets.top
        }
    }
}


class StringAdapter(private val data: List<String>) : RecyclerView.Adapter<StringViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class StringViewHolder(private val view: TextView) : ViewHolder(view) {

    fun bind(data: String) {
        view.text = data
    }
}