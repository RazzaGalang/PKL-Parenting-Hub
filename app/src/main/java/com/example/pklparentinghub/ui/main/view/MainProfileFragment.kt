package com.example.pklparentinghub.ui.main.view

import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentMainProfileBinding
import com.example.pklparentinghub.shimmer.ShimmerArticleHomeRecyclerFragment
import com.example.pklparentinghub.shimmer.ShimmerArticleProfileRecyclerFragment
import com.example.pklparentinghub.shimmer.ShimmerProfileFragment
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleProfileAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.utils.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainProfileFragment : Fragment(R.layout.fragment_main_profile) {

    private var _binding: FragmentMainProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()
        setupViewPager()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.getUserData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.data?.user?.component1().let { values ->
                            binding.apply {
                                mainProfileFullName.text = values?.fullName.toString()
                                mainProfileUsername.text = values?.username.toString()
                                mainProfileDescription.text = values?.description.toString()
                                mainProfileFollowing.text = "${values?.following.toString()} Mengikuti"
                                mainProfileFollower.text = "${values?.follower.toString()} Pengikut"

                                val profilePicture = binding.mainProfilePicture
                                val profileBanner = binding.mainProfileBanner
                                val valuesProfilePicture = values?.profilePicture
                                val valuesProfileBanner = values?.profileCover

                                Glide.with(profilePicture)
                                    .load(valuesProfilePicture)
                                    .into(profilePicture)

                                Glide.with(profileBanner)
                                    .load(valuesProfileBanner)
                                    .into(profileBanner)
                            }
                        }

                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        Log.e(ContentValues.TAG, "setupObservers: ${it.message}")
                    }
                    Status.LOADING -> {
                        Log.e(ContentValues.TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
    }

    private fun setupViewPager(){
        val viewPager: ViewPager2 = binding.mainProfileViewPager
        val tabLayout: TabLayout = binding.mainProfileTabLayout

        viewPager.adapter = MyPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Artikel Saya"
                1 -> tab.text = "Disukai"
            }
        }.attach()
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ShimmerArticleProfileRecyclerFragment()
                1 -> ShimmerArticleProfileRecyclerFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}