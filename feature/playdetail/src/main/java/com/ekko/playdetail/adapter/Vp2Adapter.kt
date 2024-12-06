package com.ekko.playdetail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(private val fragments: List<Fragment>, context: FragmentActivity) :
    FragmentStateAdapter(context) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}