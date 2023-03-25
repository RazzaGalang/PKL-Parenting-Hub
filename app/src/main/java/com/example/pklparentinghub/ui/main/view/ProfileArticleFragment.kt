package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.databinding.FragmentProfileArticleBinding
import com.example.pklparentinghub.databinding.FragmentProfileFollowersBinding
import com.example.pklparentinghub.ui.base.ContentModelFactory
import com.example.pklparentinghub.ui.base.FollowerModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileArtichleAdapter
import com.example.pklparentinghub.ui.main.adapter.ProfileFollowersAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ContentViewModel
import com.example.pklparentinghub.ui.main.viewmodel.FollowerViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileArticleFragment : Fragment() {

    private var _binding: FragmentProfileArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileArtichleAdapter = ProfileArtichleAdapter()
    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileArticleBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshArthicle)
        recyclerView = view.findViewById(R.id.articleRecycler)

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setupViewModel()
        setupObserver()
        setupUI()
    }

    private fun showLoading(loading: Boolean){
        binding.apply {
            articleRecycler.isVisible = !loading
            shimmerRecycler.isVisible = loading
        }
    }

    private fun setRecyclerViewAdapter(){
        binding.articleRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.articleRecycler.adapter = adapter
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ContentModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ContentViewModel::class.java]
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.getUserContent(token, 11).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            showLoading(resource.status == Status.LOADING)
                            when (resource.status){
                                Status.SUCCESS -> {
                                    resource.data?.let { response ->
                                        adapter.items = response.body()?.data?.articles!!
                                    }
                                }
                                Status.LOADING -> {
                                }
                                Status.ERROR -> {
                                }
                            }
                        }
                    })
                }
        }
    }

}