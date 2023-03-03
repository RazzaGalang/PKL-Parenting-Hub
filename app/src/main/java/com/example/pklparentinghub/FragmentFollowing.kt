package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pklparentinghub.databinding.FragmentFollowersBinding
import com.example.pklparentinghub.databinding.FragmentFollowingBinding

class FragmentFollowing : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val connectionFollowingAdapter = ConnectionFollowingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {
        connectionFollowingAdapter.differ.submitList(loadData())

        binding.rvConnection.layoutManager = LinearLayoutManager(this.context)
        binding.rvConnection.adapter = connectionFollowingAdapter
        binding.rvConnection.addItemDecoration(
            DividerItemDecoration(
                binding.rvConnection.context,
                (binding.rvConnection.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun loadData(): MutableList<ConnectionModel> {
        val data: MutableList<ConnectionModel> = mutableListOf()

        data.add(
            ConnectionModel(
                R.drawable.img_profile,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        data.add(
            ConnectionModel(
                R.drawable.img_profile,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        data.add(
            ConnectionModel(
                R.drawable.img_profile,
                "Razza Galang Adzan",
                "@rzzagalangs"
            )
        )

        return data
    }
}