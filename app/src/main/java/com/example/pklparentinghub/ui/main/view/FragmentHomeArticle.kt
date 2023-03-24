package com.example.pklparentinghub.ui.main.view

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
import com.example.pklparentinghub.databinding.FragmentHomeArticleBinding
import com.example.pklparentinghub.ui.base.ArticleAllViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class FragmentHomeArticle(private var popular: Boolean, private var latest: Boolean) : Fragment() {

    private var _binding: FragmentHomeArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter: ArticleHomeAdapter = ArticleHomeAdapter()
    private lateinit var viewModel : ArticleAllViewModel
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
        setupUI()
        setupViewModel()
        requestWithToken()
    }

    private fun setupUI(){
        binding.articleRecycler.adapter = adapter
        binding.detailShimmerRecycler.adapter = shimmerAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleAllViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleAllViewModel::class.java]
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            detailShimmerRecycler.isVisible = loading
            articleRecycler.isVisible = !loading
        }
    }

    private fun requestWithToken() {
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