package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentProfileFollowersBinding
import com.example.pklparentinghub.ui.base.FollowerModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileFollowersAdapter
import com.example.pklparentinghub.ui.main.viewmodel.FollowerViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileFollowersFragment : Fragment() {

    private var _binding: FragmentProfileFollowersBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileFollowersAdapter = ProfileFollowersAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileFollowersBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshFollowers)
        recyclerView = view.findViewById(R.id.profileFollowersRecycler)

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
            FollowerModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[FollowerViewModel::class.java]
    }

    private fun setRecyclerViewAdapter(){
        binding.profileFollowersRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileFollowersRecycler.adapter = adapter
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token->
                    viewModel.getUserFollower(token, 11).observe(viewLifecycleOwner, Observer {
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