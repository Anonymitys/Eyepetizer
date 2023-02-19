package com.ekko.eyepetizer.ui.userinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.RecyclerView
import com.ekko.eyepetizer.R
import kotlinx.coroutines.flow.collectLatest

/**
 * UP主作品
 * @Author Ekkoe
 * @Date 2023/2/3 18:10
 */
class UploaderWorksFragment : Fragment(R.layout.fragment_uploader_works) {

    private val uploadAdapter = UploaderWorksAdapter()
    private val model by activityViewModels<UploaderViewModel>()
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val url = arguments?.getString(ACTION_URL, "") ?: ""
        view.findViewById<RecyclerView>(R.id.list).apply {
            adapter = uploadAdapter
            // isNestedScrollingEnabled = false
        }
        lifecycleScope.launchWhenResumed{
            try {
                model.getWorkFlow(url).collectLatest {
                    uploadAdapter.submitData(it)
                }
            } catch (e: Exception) {
                Log.e("huqiang", "onViewCreated: $e")
            }
        }
    }

    companion object {
        private const val ACTION_URL = "action_url"
        fun newInstance(url: String): UploaderWorksFragment = UploaderWorksFragment().apply {
            arguments = bundleOf(ACTION_URL to url)
        }
    }
}