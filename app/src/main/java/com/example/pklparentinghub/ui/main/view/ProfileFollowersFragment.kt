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
import com.example.pklparentinghub.data.model.profile.ProfileConnectionModel
import com.example.pklparentinghub.databinding.FragmentProfileFollowersBinding
import com.example.pklparentinghub.ui.main.adapter.ProfileFollowersAdapter

class ProfileFollowersFragment : Fragment() {

    private var _binding: FragmentProfileFollowersBinding? = null
    private val binding get() = _binding!!
    private val connectionAdapter = ProfileFollowersAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

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
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setRecyclerViewAdapter(){
        connectionAdapter.differ.submitList(loadData())

        binding.profileFollowersRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileFollowersRecycler.adapter = connectionAdapter
        binding.profileFollowersRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.profileFollowersRecycler.context,
                (binding.profileFollowersRecycler.layoutManager as LinearLayoutManager).orientation
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
            ))

        data.add(ProfileConnectionModel(
            R.drawable.img_auth_profile_default_picture,
            "Razza Galang Adzan",
            "@rzzagalangs"
        ))

        data.add(ProfileConnectionModel(
            R.drawable.img_auth_profile_default_picture,
            "Razza Galang Adzan",
            "@rzzagalangs"
        ))

        return data
    }
}