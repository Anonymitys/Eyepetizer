package com.ekko.eyepetizer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekko.eyepetizer.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vp2.adapter =
            Adapter(
                requireParentFragment(),
                listOf(RecommendFragment(), FollowFragment(), DailyIssueFragment())
            )
        TabLayoutMediator(binding.tabLayout, binding.vp2) { tab, pos ->
            when (pos) {
                0 -> tab.text = "推荐"
                1 -> tab.text = "关注"
                2 -> tab.text = "日报"
            }
        }.attach()
    }


}

class Adapter(fragment: Fragment, private val list: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}