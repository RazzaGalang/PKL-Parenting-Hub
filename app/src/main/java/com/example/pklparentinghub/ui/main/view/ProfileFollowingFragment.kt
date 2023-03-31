package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.userFollowing.User
import com.example.pklparentinghub.databinding.FragmentProfileFollowingBinding
import com.example.pklparentinghub.ui.base.FollowModelFactory
import com.example.pklparentinghub.ui.base.FollowingModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileFollowingAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerProfileFollowAdapter
import com.example.pklparentinghub.ui.main.viewmodel.FollowViewModel
import com.example.pklparentinghub.ui.main.viewmodel.FollowingViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileFollowingFragment : Fragment(), ProfileFollowingAdapter.OnItemClickListener {

    private var _binding: FragmentProfileFollowingBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileFollowingAdapter = ProfileFollowingAdapter(this)
    private val shimmerAdapter : ShimmerProfileFollowAdapter = ShimmerProfileFollowAdapter()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FollowingViewModel
    private lateinit var viewModelFollow : FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshFollowing)
        recyclerView = view.findViewById(R.id.profileFollowingRecycler)

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

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            FollowingModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[FollowingViewModel::class.java]

        viewModelFollow = ViewModelProvider(
            this,
            FollowModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[FollowViewModel::class.java]
    }

    private fun setRecyclerViewAdapter(){
        binding.profileFollowingRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileFollowingRecycler.adapter = adapter
    }

    override fun onItemClick(item: User) {
        setupFollow(item)
    }

    private fun setupFollow(item: User){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModelFollow.postUserFollow(token, item.id).observe(viewLifecycleOwner, Observer {
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
                                    Log.e(ContentValues.TAG, "setupFollow: ", )
                                }
                            }
                        }
                    })
                }
        }
    }

    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    private fun showLoading(loading: Boolean){
        binding.apply {
            profileFollowingRecycler.isVisible = !loading
            shimmerRecycler.isVisible = loading
        }
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token->
                    lifecycleScope.launchWhenResumed {
                        AccessManager(requireContext())
                            .accessUserId
                            .collect { userId ->
                                viewModel.getUserFollowings(token, userId).observe(viewLifecycleOwner, Observer {
                                    it?.let { resource ->
                                        showLoading( resource.status == Status.LOADING)
                                        when (resource.status) {
                                            Status.SUCCESS -> {
                                                resource.data?.let { response ->
                                                    binding.apply {
                                                        adapter.items = response.body()?.data?.user!!
                                                    }
                                                }
                                                if (adapter.items.isEmpty()){
                                                    binding.clFollowing.isVisible = true
                                                    binding.profileFollowingRecycler.isVisible = false
                                                    binding.shimmerRecycler.isVisible = false
                                                } else {
                                                    binding.clFollowing.isVisible = false
                                                    binding.profileFollowingRecycler.isVisible = true
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
}