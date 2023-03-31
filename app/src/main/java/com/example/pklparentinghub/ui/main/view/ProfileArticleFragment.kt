package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.databinding.FragmentProfileArticleBinding
import com.example.pklparentinghub.ui.base.ContentModelFactory
import com.example.pklparentinghub.ui.base.LikeModelFactory
import com.example.pklparentinghub.ui.main.adapter.ProfileArticleAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ContentViewModel
import com.example.pklparentinghub.ui.main.viewmodel.LikeViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class ProfileArticleFragment : Fragment(), ProfileArticleAdapter.OnItemClickListener {

    private var _binding: FragmentProfileArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter : ProfileArticleAdapter = ProfileArticleAdapter(this)
    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ContentViewModel
    private lateinit var viewModelLike: LikeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileArticleBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.articleRecycler)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setupViewModel()
        setupObserver()
        setupUI()
    }

    private fun setRecyclerViewAdapter(){
        binding.articleRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.articleRecycler.adapter = adapter
    }

    private fun showLoading(loading: Boolean){
        binding.apply {
            articleRecycler.isVisible = !loading
            shimmerRecycler.isVisible = loading
        }
    }

    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    override fun onItemClick(item: Article) {
        setupLike(item)
    }

    private fun setupLike(item: Article){
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
                                    Log.e(ContentValues.TAG, "setupFollow: ", )
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
                            viewModel.getUserContent(token, userId).observe(viewLifecycleOwner, Observer {
                                it?.let { resource ->
                                    showLoading(resource.status == Status.LOADING)
                                    when (resource.status){
                                        Status.SUCCESS -> {
                                            resource.data?.let { response ->
                                                binding.apply {
                                                    adapter.items = response?.body()?.data?.articles!!
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

}