package com.ekko.eyepetizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekko.eyepetizer.databinding.ActivityMainBinding
import com.ekko.eyepetizer.ui.home.HomeFragment
import com.ekko.eyepetizer.ui.home.MineFragment
import com.ekko.eyepetizer.ui.home.SearchFragment
import com.ekko.eyepetizer.ui.home.SquareFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        splashScreen = installSplashScreen()
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