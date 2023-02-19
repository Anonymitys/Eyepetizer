package com.ekko.eyepetizer.ui.userinfo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ekko.eyepetizer.R
import com.ekko.repository.api.core.apiService
import com.ekko.repository.model.UploaderInfo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UploaderActivity : AppCompatActivity() {

    private val model by viewModels<UploaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_uploader)
        val toolbar = findViewById<MaterialToolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val collapsingToolbarLayout =
            findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        findViewById<AppBarLayout>(
            R.id.app_layout
        ).addOnOffsetChangedListener { _, verticalOffset ->
            val toolbarShow =
                collapsingToolbarLayout.height + verticalOffset < collapsingToolbarLayout.scrimVisibleHeightTrigger
            supportActionBar?.setDisplayShowTitleEnabled(toolbarShow)
            toolbar.setNavigationIconTint(
                getColor(if (toolbarShow) R.color.black else R.color.white)
            )
        }

        lifecycleScope.launchWhenResumed {
            try {
                val data = apiService.uploaderApi.getUploaderInfo("2164", "PGC")
                updateHeader(data)
                updateContent(data)
            } catch (e: Exception) {
                Log.e("huqiang", "onCreate: $e")
            }

        }
    }

    private fun updateHeader(info: UploaderInfo) {
        Glide.with(this).load(info.pgcInfo.cover).into(findViewById(R.id.uploader_bg))

        Glide.with(this)
            .load(info.pgcInfo.icon)
            .transform(CircleCrop())
            .into(findViewById(R.id.uploader_cover))

        findViewById<TextView>(R.id.uploader_title).text = info.pgcInfo.name
        findViewById<TextView>(R.id.uploader_desc).text = info.pgcInfo.description
        findViewById<TextView>(R.id.uploader_brief).text = info.pgcInfo.brief
        supportActionBar?.title = info.pgcInfo.name
    }

    private fun updateContent(data: UploaderInfo) {
        val tabs = findViewById<TabLayout>(R.id.tabs)
        val vp2 = findViewById<ViewPager2>(R.id.vp2_uploader).apply {
            adapter = Vp2Adapter(
                this@UploaderActivity, listOf(
                UploaderHomeFragment.newInstance(data.tabInfo.tabList[0].apiUrl),
                UploaderWorksFragment.newInstance(data.tabInfo.tabList[1].apiUrl)
            )
            )
        }
        TabLayoutMediator(tabs, vp2) { tab, position ->
            tab.text = data.tabInfo.tabList[position].name
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}

class Vp2Adapter(
    context: FragmentActivity,
    private val list: List<Fragment>
) : FragmentStateAdapter(context) {

    override fun createFragment(position: Int): Fragment = list[position]

    override fun getItemCount(): Int = list.size
}