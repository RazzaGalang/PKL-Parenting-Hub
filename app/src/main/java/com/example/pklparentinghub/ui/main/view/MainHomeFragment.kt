package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.model.ImageData
import com.example.pklparentinghub.databinding.FragmentMainHomeBinding
import com.example.pklparentinghub.shimmer.ShimmerArticleHomeRecyclerFragment
import com.example.pklparentinghub.shimmer.ShimmerArticleProfileRecyclerFragment
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeArticleSliderAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainHomeFragment : Fragment(R.layout.fragment_main_home) {

    private var _binding: FragmentMainHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArticleHomeArticleSliderAdapter
    private val list = ArrayList<ImageData>()
    private lateinit var dots: ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setUpArticleSlider()
    }

    private fun setUpArticleSlider(){
        list.add(ImageData(R.drawable.img_rv_horizontal, getString(R.string.example_title_article)))
        list.add(ImageData(R.drawable.img_rv_horizontal, getString(R.string.example_title_article)))
        list.add(ImageData(R.drawable.img_rv_horizontal, getString(R.string.example_title_article)))

        adapter = ArticleHomeArticleSliderAdapter(list)
        binding.articleSlider.adapter = adapter
        dots = ArrayList()
        setIndicator()

        binding.articleSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDot(position: Int) {
        for(i in 0 until list.size){
            if(i == position)
                dots[i].setTextColor(ContextCompat.getColor(this.requireContext(), R.color.primary50))
            else
                dots[i].setTextColor(ContextCompat.getColor(this.requireContext(), R.color.grey))
        }
    }

    private fun setIndicator() {
        for(i in 0 until list.size){
            dots.add(TextView(this.context))
            dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            dots[i].textSize = 16f
            binding.indicatorSlider.addView(dots[i])
        }
    }

    private fun setUpViewPager(){
        val viewPager: ViewPager2 = binding.homeViewPager
        val tabLayout: TabLayout = binding.homeTabLayout

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