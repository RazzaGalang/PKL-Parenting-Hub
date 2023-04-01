package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.userContent.LikedArticle
import com.example.pklparentinghub.databinding.FragmentProfileLikesBinding
import com.example.pklparentinghub.ui.base.ContentModelFactory
import com.example.pklparentinghub.ui.base.LikeModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileLikesAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ContentViewModel
import com.example.pklparentinghub.ui.main.viewmodel.LikeViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileLikesFragment : Fragment(), ProfileLikesAdapter.OnItemClickListener {

    private var _binding: FragmentProfileLikesBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileLikesAdapter = ProfileLikesAdapter(this)
    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ContentViewModel
    private lateinit var viewModelLike : LikeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileLikesBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.profileRecycler)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setupViewModel()
        setupObserver()
        setupUI()
    }

    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    override fun onItemClick(item: LikedArticle) {
        setupLike(item)
    }

    private fun setRecyclerViewAdapter(){

        binding.profileRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileRecycler.adapter = adapter
    }

    private fun showLoading(loading: Boolean){
        binding.apply {
            profileRecycler.isVisible = !loading
            shimmerRecycler.isVisible = loading
        }
    }

    private fun setupLike(item: LikedArticle){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect{ token ->
                    viewModelLike.postUserLike(token, item.id).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when(resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { response ->
                                        binding.apply {
                                        }
                                    }
                                    setupObserver()
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ContentModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ContentViewModel::class.java]

        viewModelLike = ViewModelProvider(
            this,
            LikeModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LikeViewModel::class.java]
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    AccessManager(requireContext())
                        .accessUserId
                        .collect { userId ->
                            viewModel.getUserContent(token, userId)
                                .observe(viewLifecycleOwner, Observer {
                                    it?.let { resource ->
                                        showLoading(resource.status == Status.LOADING)
                                        when (resource.status) {
                                            Status.SUCCESS -> {
                                                resource.data?.let { response ->
                                                    binding.apply {
                                                        adapter.items =
                                                            response?.body()?.data?.likedArticles!!
                                                    }
                                                }
                                                if (adapter.items.isEmpty()){
                                                    binding.ivEmptyState.isVisible = true
                                                    binding.tvEmptyState.isVisible = true
                                                    binding.shimmerRecycler.isVisible = false
                                                    binding.shimmerRecycler.isVisible = false
                                                } else {
                                                    binding.ivEmptyState.isVisible = false
                                                    binding.tvEmptyState.isVisible = false
                                                    binding.shimmerRecycler.isVisible = true
                                                    binding.shimmerRecycler.isVisible = false
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
}