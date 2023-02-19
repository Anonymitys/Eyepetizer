package com.ekko.eyepetizer.ui.home.daily

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ekko.eyepetizer.R
import kotlinx.coroutines.flow.collectLatest

/**
 * 日报
 * @Author Ekkoe
 * @Date 2023/1/31 11:25
 */
class DailyFragment : Fragment(R.layout.fragment_daily) {
    private val model by viewModels<DailyViewModel>()
    private val adapter = DailyAdapter()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        view.findViewById<RecyclerView>(R.id.list).adapter = adapter
        lifecycleScope.launchWhenCreated {
            model.flow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}