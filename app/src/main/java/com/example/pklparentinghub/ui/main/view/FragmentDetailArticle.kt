package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentDetailArticleBinding
import com.example.pklparentinghub.ui.base.ArticleAllViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeSliderAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class FragmentDetailArticle : Fragment() {

    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ArticleAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailArticleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOtherArticle()
        setupViewModel()
        requestWithToken()
    }

    private fun setupOtherArticle() {
        val fragment = ProfileArticleFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameRecyclerAnotherArticle, fragment)?.commit()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleAllViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleAllViewModel::class.java]
    }

    private fun requestWithToken() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}