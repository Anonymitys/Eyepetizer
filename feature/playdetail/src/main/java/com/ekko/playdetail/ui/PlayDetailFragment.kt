package com.ekko.playdetail.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekko.play.detail.databinding.FragmentPlayDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class PlayDetailFragment : Fragment() {

    private lateinit var binding: FragmentPlayDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = activity as? AppCompatActivity
        context?.setSupportActionBar(binding.toolBar)
        context?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.vp2.adapter =
            VpAdapter(listOf(ContentFragment(), CommentFragment()), requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.vp2) { tab, position ->
            when (position) {
                0 -> tab.text = "简介"
                1 -> tab.text = "评论"
            }
        }.attach()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("huqiang", "onConfigurationChanged: $newConfig")
    }
}


class VpAdapter(private val fragments: List<Fragment>, context: FragmentActivity) :
    FragmentStateAdapter(context) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}