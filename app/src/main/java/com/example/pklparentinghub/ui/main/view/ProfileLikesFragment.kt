package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentProfileLikesBinding
import com.example.pklparentinghub.ui.main.adapter.ProfileArtichleAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter

class ProfileLikesFragment : Fragment() {

    private var _binding: FragmentProfileLikesBinding? = null
    private val binding get() = _binding!!
    private val profileAdapter = ProfileArtichleAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileLikesBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshArthicle)
        recyclerView = view.findViewById(R.id.profileRecycler)

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setRecyclerViewAdapter()
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }

    private fun setRecyclerViewAdapter(){

        binding.profileRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileRecycler.adapter = profileAdapter
        binding.profileRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.profileRecycler.context,
                (binding.profileRecycler.layoutManager as LinearLayoutManager).orientation
            )
        )
    }
}