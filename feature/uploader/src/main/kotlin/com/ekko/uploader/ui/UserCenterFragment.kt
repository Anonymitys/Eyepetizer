package com.ekko.uploader.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekko.page.fragment.PageListFragment
import com.therouter.TheRouter
import com.therouter.router.KEY_PATH
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint


@Route(path = "eyepetizer://page/userProfile")
@AndroidEntryPoint
class UserCenterFragment : PageListFragment() {

    private lateinit var pageType: String
    private lateinit var pageLabel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = Uri.parse(arguments?.getString(KEY_PATH, "") ?: "")
        pageType = uri.getQueryParameter(PAGE_TYPE) ?: ""
        pageLabel = uri.getQueryParameter(PAGE_LABEL) ?: ""
    }

    override val pageParams: Pair<String, String>
        get() = Pair(pageType, pageLabel)

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    override fun navigateTo(view: View, url: String) {
        TheRouter.build(url).navigation()
    }

    override fun loadingType(): Int {
        return REFRESH_LOADING
    }


    companion object {
        const val PAGE_TYPE = "page_type"
        const val PAGE_LABEL = "page_label"
    }
}