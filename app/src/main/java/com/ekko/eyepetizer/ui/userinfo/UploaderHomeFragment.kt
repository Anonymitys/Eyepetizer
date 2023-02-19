package com.ekko.eyepetizer.ui.userinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ekko.eyepetizer.R
import com.ekko.repository.api.core.apiService

/**
 * up主首页
 * @Author Ekkoe
 * @Date 2023/2/15 16:58
 */
class UploaderHomeFragment : Fragment(R.layout.fragment_uploader_works) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val url = arguments?.getString(ACTION_URL, "") ?: ""
        Log.e("huqiang", "onViewCreated: ${url}")
        lifecycleScope.launchWhenResumed {
            try {
                val data = apiService.uploaderApi.getUploaderHome(url)
                Log.e("huqiang", "onViewCreated: ${data.count}")
            } catch (e: Exception) {
                Log.e("huqiang", "onViewCreated: ${e}")
            }
        }
    }

    companion object {
        private const val ACTION_URL = "action_url"
        fun newInstance(url: String): UploaderHomeFragment = UploaderHomeFragment().apply {
            arguments = bundleOf(ACTION_URL to url)
        }
    }
}