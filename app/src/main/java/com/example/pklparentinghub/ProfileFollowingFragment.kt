package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pklparentinghub.databinding.FragmentProfileFollowingBinding

class ProfileFollowingFragment : Fragment() {
    private var _binding: FragmentProfileFollowingBinding? = null
    private val binding get() = _binding!!
    private val connectionAdapter = ProfileFollowingAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

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
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setRecyclerViewAdapter(){
        connectionAdapter.differ.submitList(loadData())

        binding.profileFollowingRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileFollowingRecycler.adapter = connectionAdapter
        binding.profileFollowingRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.profileFollowingRecycler.context,
                (binding.profileFollowingRecycler.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun loadData(): MutableList<ProfileConnectionModel>{
        val data : MutableList<ProfileConnectionModel> = mutableListOf()

        data.add(
            ProfileConnectionModel(
                R.drawable.img_auth_profile_default_picture,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        data.add(
            ProfileConnectionModel(
                R.drawable.img_auth_profile_default_picture,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        data.add(
            ProfileConnectionModel(
                R.drawable.img_auth_profile_default_picture,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        return data
    }
}