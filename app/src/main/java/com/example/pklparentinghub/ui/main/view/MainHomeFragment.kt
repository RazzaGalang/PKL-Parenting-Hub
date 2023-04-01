package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.databinding.FragmentMainHomeBinding
import com.example.pklparentinghub.ui.base.ArticleAllViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeSliderAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.example.pklparentinghub.utils.Status.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainHomeFragment : Fragment(), ArticleHomeSliderAdapter.OnItemClickListener {

    private var _binding: FragmentMainHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: ArticleHomeSliderAdapter = ArticleHomeSliderAdapter(this)
    private lateinit var viewModel : ArticleAllViewModel

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
        setUpSearch()
        initListView()
        setupViewModel()
        requestWithToken()
    }

    override fun onItemClick(item: Article) {
        val intent = Intent(this.context, DetailArticleActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }

    private fun setUpSearch() {
        binding.etSearchHome.setOnClickListener {
            val fragment = FragmentSearchArticle()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }

    private fun initListView(){
        binding.articleSlider.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleAllViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleAllViewModel::class.java]
    }

    private fun showLoading(loading: Boolean) {
        binding.articleSliderLoading.isVisible = loading
        binding.articleSlider.isVisible = !loading
    }

    private fun requestWithToken() {
        lifecycleScope.launchWhenCreated {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.getArticleAll(token, 3, "", true, false).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            showLoading(resource.status == Status.LOADING)
                            when (resource.status) {
                                SUCCESS -> {
                                    resource.data?.let { article -> adapter.items = article.data.article }
                                }
                                ERROR -> {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                                }
                                LOADING -> {}
                            }
                        }
                    })
                }
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

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FragmentHomeArticle(false, true)
                1 -> FragmentHomeArticle(true, false)
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}