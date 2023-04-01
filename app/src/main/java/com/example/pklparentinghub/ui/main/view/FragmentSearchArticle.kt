package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.databinding.FragmentSearchArticleBinding
import com.example.pklparentinghub.ui.base.ArticleSearchViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleSearchAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleSearchViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class FragmentSearchArticle : Fragment(), ArticleSearchAdapter.OnItemClickListener {

    private var _binding: FragmentSearchArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : ArticleSearchViewModel
    private val adapter: ArticleSearchAdapter = ArticleSearchAdapter(this)
    private val shimmerAdapter: ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearch()
        initListView()
        setupViewModel()
        onBackClick()
        onBackPressed()
    }

    override fun onItemClick(item: Article) {
        val intent = Intent(this.context, DetailArticleActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }

    private fun setUpSearch() {
        binding.etSearchArticle.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = v.text.toString()
                setUpObserveResult(searchText)
                true
            } else {
                false
            }
        }
    }

    private fun initListView(){
        binding.rvArticle.adapter = adapter
        binding.rvArticle.addItemDecoration(
            DividerItemDecoration(
                binding.rvArticle.context,
                (binding.rvArticle.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvArticleLoading.adapter = shimmerAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleSearchViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleSearchViewModel::class.java]
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            rvArticleLoading.isVisible = loading
            rvArticle.isVisible = !loading
        }
    }

    private fun setUpObserveResult(search: String) {
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.getArticleSearch(token, search).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            showLoading(resource.status == Status.LOADING)
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { article -> adapter.items = article.data.article }
                                    if (adapter.items.isEmpty()){
                                        binding.clEmptyState.isVisible = true
                                        binding.nsvInitial.isVisible = false
                                    } else {
                                        binding.clEmptyState.isVisible = false
                                        binding.rvArticle.isVisible = true
                                        binding.rvArticleLoading.isVisible = false
                                    }
                                }
                                Status.ERROR -> {
                                    binding.clConnectionError.isVisible = true
                                    binding.nsvInitial.isVisible = false
                                    Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_LONG).show()
                                }
                                Status.LOADING -> {}
                            }
                        }
                    })
                }
        }
    }

    private fun back(){
        val fragment = MainHomeFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
    }

    private fun onBackClick(){
        binding.topAppBar.setNavigationOnClickListener {
            back()
        }
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


