package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.pklparentinghub.databinding.FragmentProfileFollowingBinding
import com.example.pklparentinghub.ui.base.FollowingModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileFollowingAdapter
import com.example.pklparentinghub.ui.main.viewmodel.FollowingViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileFollowingFragment : Fragment() {

    private var _binding: FragmentProfileFollowingBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileFollowingAdapter = ProfileFollowingAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            FollowingModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[FollowingViewModel::class.java]
    }

    private fun setRecyclerViewAdapter(){
        binding.profileFollowingRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileFollowingRecycler.adapter = adapter
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token->
                    viewModel.getUserFollowings(token, 11).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { response ->
                                        binding.apply {
                                            adapter.items = response?.body()?.data?.user!!
                                        }
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