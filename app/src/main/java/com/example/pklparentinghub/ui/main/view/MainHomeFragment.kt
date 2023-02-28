package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.R
import com.example.pklparentinghub.shimmer.ShimmerArticleHomeRecyclerFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainHomeFragment : Fragment(R.layout.fragment_main_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = view.findViewById(R.id.homeViewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.homeTabLayout)

        MyPagerAdapter(requireActivity()).also { viewPager.adapter = it }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Terbaru"
                1 -> tab.text = "Terpopuler"
            }
        }.attach()
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ShimmerArticleHomeRecyclerFragment()
                1 -> ShimmerArticleHomeRecyclerFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
        }
}