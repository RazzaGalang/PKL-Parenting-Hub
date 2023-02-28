package com.example.pklparentinghub.shimmer

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentShimmerArticleProfileRecyclerBinding
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.utils.Status
import java.util.*

class ShimmerArticleProfileRecyclerFragment : Fragment() {

    private var _binding: FragmentShimmerArticleProfileRecyclerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private val adapter : ArticleProfileAdapter = ArticleProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShimmerArticleProfileRecyclerBinding.inflate(inflater, container, false)
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
        binding.shimmerRecycler.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUserData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}