package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentProfileArticleBinding
import com.example.pklparentinghub.databinding.FragmentShimmerArticleHomeRecyclerBinding
import com.example.pklparentinghub.databinding.FragmentShimmerArticleProfileRecyclerBinding
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleProfileAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.utils.Status

class ProfileArticleFragment : Fragment() {

    private var _binding: FragmentProfileArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private val adapter : ArticleProfileAdapter = ArticleProfileAdapter()
    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ProfileViewModel::class.java]
    }

    private fun setupUI() {
        binding.profileRecycler.adapter = adapter
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    private fun showLoading(loading: Boolean) {
        binding.profileRecycler.isVisible = !loading
        binding.shimmerRecycler.isVisible = loading
    }

    private fun setupObservers() {
        viewModel.getUserData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                showLoading(resource.status == Status.LOADING)
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users -> adapter.items = users.data.user.component1().article }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, "setupObservers: " + it.message)
                    }
                    Status.LOADING -> {
                        Log.e(ContentValues.TAG, "setupObservers: LOADING")

                    }
                }
            }
        })
    }

}



