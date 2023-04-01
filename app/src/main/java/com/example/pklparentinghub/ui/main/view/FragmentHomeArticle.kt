package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.databinding.FragmentHomeArticleBinding
import com.example.pklparentinghub.ui.base.ArticleAllViewModelFactory
import com.example.pklparentinghub.ui.base.LikeModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.ui.main.viewmodel.LikeViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class FragmentHomeArticle(var popular: Boolean, private var latest: Boolean) : Fragment(), ArticleHomeAdapter.OnItemClickListener {

    private var _binding: FragmentHomeArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : ArticleAllViewModel
    private lateinit var likeViewModel: LikeViewModel
    private val adapter: ArticleHomeAdapter = ArticleHomeAdapter(this)
    private val shimmerAdapter: ShimmerArticleHomeAdapter = ShimmerArticleHomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListView()
        setupViewModel()
        setupObserver()
        swipeRefresh()
    }

    override fun onItemClick(item: Article) {
        val intent = Intent(this.context, DetailArticleActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }

    override fun onItemLike(item: Article) {
        setupLike(item)
    }

    private fun initListView(){
        binding.articleRecycler.adapter = adapter
        binding.detailShimmerRecycler.adapter = shimmerAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleAllViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleAllViewModel::class.java]

        likeViewModel = ViewModelProvider(
            this,
            LikeModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LikeViewModel::class.java]
    }

    private fun swipeRefresh(){
        binding.swipeRefreshHome.setOnRefreshListener {
            setupObserver()
            binding.swipeRefreshHome.isRefreshing = true
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            detailShimmerRecycler.isVisible = loading
            articleRecycler.isVisible = !loading
        }
    }

    private fun setupLike(item: Article){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect{ token ->
                    likeViewModel.postUserLike(token, item.id).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when(resource.status) {
                                Status.SUCCESS -> {
                                    setupObserver()
                                }
                                Status.LOADING -> {}
                                Status.ERROR -> {
                                    Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    })
                }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.getArticleAll(token, 15, "", popular, latest).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            showLoading(resource.status == Status.LOADING)
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { article -> adapter.items = article.data.article }
                                    binding.swipeRefreshHome.isRefreshing = false
                                }
                                Status.ERROR -> {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                                }
                                Status.LOADING -> {}
                            }
                        }
                    })
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}