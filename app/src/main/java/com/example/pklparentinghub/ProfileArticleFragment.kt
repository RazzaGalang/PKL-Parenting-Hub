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
import com.example.pklparentinghub.databinding.FragmentProfileArtichleBinding

class ProfileArticleFragment : Fragment() {

    private var _binding: FragmentProfileArtichleBinding? = null
    private val binding get() = _binding!!
    private val profileAdapter = ProfileArthicleAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileArtichleBinding.inflate(inflater, container, false)
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
        setRecyclerViewAdapter()
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setRecyclerViewAdapter(){
        profileAdapter.differ.submitList(loadData())

        binding.profileRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.profileRecycler.adapter = profileAdapter
        binding.profileRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.profileRecycler.context,
                (binding.profileRecycler.layoutManager as LinearLayoutManager).orientation
            )
        )
    }
    private fun loadData(): MutableList<ProfileModel>{
        val data : MutableList<ProfileModel> = mutableListOf()

        data.add(ProfileModel(
            R.drawable.img_auth_profile_default_picture,
            "Razza Galang Adzan",
            getString(R.string.desc_art),
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default_arthicle_cover
        ))
        data.add(ProfileModel(
            R.drawable.img_auth_profile_default_picture,
            "Razza Galang",
            getString(R.string.desc_art),
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default_arthicle_cover
        ))
        data.add(ProfileModel(
            R.drawable.img_auth_profile_default_picture,
            "Razza",
            getString(R.string.desc_art),
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default_arthicle_cover
        ))
        data.add(ProfileModel(
            R.drawable.img_auth_profile_default_picture,
            "Galang Adzan",
            getString(R.string.desc_art),
            "178 suka",
            "7 Februari 2023",
            R.drawable.img_default_arthicle_cover
        ))

        return data
    }
}