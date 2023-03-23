package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.pklparentinghub.databinding.FragmentHomeArticleBinding
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter

class FragmentHomeArticle : Fragment() {

    private var _binding: FragmentHomeArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter: ArticleHomeAdapter = ArticleHomeAdapter()
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
    }

    private fun setupUI(){
        binding.articleRecycler.adapter = adapter
        binding.detailShimmerRecycler.adapter = shimmerAdapter
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            detailShimmerRecycler.isVisible = loading
            articleRecycler.isVisible = !loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}