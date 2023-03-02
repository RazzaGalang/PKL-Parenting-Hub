package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.R
import com.example.pklparentinghub.shimmer.ShimmerArticleHomeRecyclerFragment
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeHorizontalAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainHomeFragment : Fragment(R.layout.fragment_main_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar : Toolbar = view.findViewById(R.id.topAppBar)
        activity.setSupportActionBar(toolbar)

        val viewPagerHorizontal: ViewPager = view.findViewById(R.id.articlePager)
        setArticlePager(viewPagerHorizontal)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bottom_navigation, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Cari di sini..."

        // Handle search query submission and change callbacks here
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

    private fun setArticlePager(viewPager: ViewPager){
        val imageUrls = listOf(
            R.drawable.img_rv_horizontal,
            R.drawable.img_rv_horizontal,
            R.drawable.img_rv_horizontal
        )
        val titleArticle = listOf(
            getString(R.string.example_title_article),
            getString(R.string.example_title_article),
            getString(R.string.example_title_article)
        )
        viewPager.adapter = ArticleHomeHorizontalAdapter(requireContext(), imageUrls, titleArticle)
    }
}