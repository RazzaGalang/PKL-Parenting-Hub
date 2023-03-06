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
import com.example.pklparentinghub.databinding.FragmentConnectionBinding
import com.example.pklparentinghub.databinding.FragmentFollowersBinding

class FragmentFollowers : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val connectionAdapter = ConnectionAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshFollowers)
        recyclerView = view.findViewById(R.id.rvConnection)

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

        binding.rvConnection.layoutManager = LinearLayoutManager(this.context)
        binding.rvConnection.adapter = connectionAdapter
        binding.rvConnection.addItemDecoration(
            DividerItemDecoration(
                binding.rvConnection.context,
                (binding.rvConnection.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun loadData(): MutableList<ConnectionModel>{
        val data : MutableList<ConnectionModel> = mutableListOf()

        data.add(
            ConnectionModel(
            R.drawable.img_profile,
            "Razza Galang Adzan",
            "@rzzagalangs"
        ))

        data.add(ConnectionModel(
            R.drawable.img_profile,
            "Razza Galang Adzan",
            "@rzzagalangs"
        ))

        data.add(ConnectionModel(
            R.drawable.img_profile,
            "Razza Galang Adzan",
            "@rzzagalangs"
        ))

        return data
    }
}