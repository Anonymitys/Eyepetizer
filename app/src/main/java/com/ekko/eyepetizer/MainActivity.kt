package com.ekko.eyepetizer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ekko.eyepetizer.databinding.ActivityMainBinding
import com.ekko.eyepetizer.page.PageViewModel
import com.ekko.eyepetizer.ui.home.HomeFragment
import com.ekko.eyepetizer.ui.home.MineFragment
import com.ekko.eyepetizer.ui.home.SearchFragment
import com.ekko.eyepetizer.ui.home.SquareFragment
import com.ekko.eyepetizer.ui.theme.EyepetizerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vp2.apply {
            isUserInputEnabled = false
            adapter = PageAdapter(this@MainActivity)
        }

        binding.navView.setOnItemSelectedListener {
            val position = (binding.vp2.adapter as PageAdapter).getPosition(it.itemId)
            binding.vp2.setCurrentItem(position, false)
            true
        }

    }
}


class PageAdapter(context: FragmentActivity) : FragmentStateAdapter(context) {

    private val fragments =
        listOf(HomeFragment(), SquareFragment(), SearchFragment(), MineFragment())

    private val fragmentIds = listOf(
        R.id.navigation_home, R.id.navigation_square, R.id.navigation_search, R.id.navigation_mine
    )

    override fun createFragment(position: Int): Fragment = fragments[position]

    override fun getItemCount(): Int = fragments.size

    fun getPosition(id: Int) = fragmentIds.indexOf(id)

}