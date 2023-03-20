package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentMainProfileBinding
import com.example.pklparentinghub.shimmer.ShimmerArticleProfileRecyclerFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainProfileFragment : Fragment(R.layout.fragment_main_profile) {

    private var _binding: FragmentMainProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }


    private fun setupViewPager(){
        val viewPager: ViewPager2 = binding.mainProfileViewPager
        val tabLayout: TabLayout = binding.mainProfileTabLayout

        viewPager.adapter = MyPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Artikel Saya"
                1 -> tab.text = "Disukai"
            }
        }.attach()
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfileArticleFragment()
                1 -> ShimmerArticleProfileRecyclerFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}