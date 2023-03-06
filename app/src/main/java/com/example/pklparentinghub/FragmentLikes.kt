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
import com.example.pklparentinghub.databinding.FragmentArtichleBinding
import com.example.pklparentinghub.databinding.FragmentLikesBinding


class FragmentLikes : Fragment() {

    private var _binding: FragmentLikesBinding? = null
    private val binding get() = _binding!!
    private val profileAdapter = ProfileAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikesBinding.inflate(inflater, container, false)
        val view = binding.root

        swipeRefreshLayout = view.findViewById(R.id.refreshLikes)
        recyclerView = view.findViewById(R.id.rvArtichle)

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
        profileAdapter.differ.submitList(loadData())

        binding.rvArtichle.layoutManager = LinearLayoutManager(this.context)
        binding.rvArtichle.adapter = profileAdapter
        binding.rvArtichle.addItemDecoration(
            DividerItemDecoration(
                binding.rvArtichle.context,
                (binding.rvArtichle.layoutManager as LinearLayoutManager).orientation
            )
        )
    }
    private fun loadData(): MutableList<ProfileModel>{
        val data : MutableList<ProfileModel> = mutableListOf()

        data.add(ProfileModel(
            R.drawable.img_profile,
            "Razza Galang Adzan",
            getString(R.string.desc_art),
            R.drawable.ic_like_dark,
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default
        ))
        data.add(ProfileModel(
            R.drawable.img_profile,
            "Razza Galang",
            getString(R.string.desc_art),
            R.drawable.ic_like,
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default
        ))
        data.add(ProfileModel(
            R.drawable.img_profile,
            "Razza",
            getString(R.string.desc_art),
            R.drawable.ic_like_dark,
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default
        ))
        data.add(ProfileModel(
            R.drawable.img_profile,
            "Galang Adzan",
            getString(R.string.desc_art),
            R.drawable.ic_like,
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default
        ))

        return data
    }
}