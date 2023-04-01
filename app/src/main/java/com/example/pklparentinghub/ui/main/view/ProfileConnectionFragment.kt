package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentProfileConnectionBinding
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileConnectionFragment : Fragment() {

    private var _binding: FragmentProfileConnectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileConnectionBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        setupObserver()
        setupViewModel()
        navBar()
        floatingButton()
        back()
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfileFollowingFragment()
                1 -> ProfileFollowersFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

    private fun navBar(){
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
    }

    private fun floatingButton(){
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.mainButtonCreateArticle)
        fab.visibility = View.GONE
    }

    private fun back(){
        binding.topBarProfileConnection.setOnClickListener {
            val fragment = MainProfileFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }
    private fun setupPager(){
        val viewPager: ViewPager2 = binding.profileCOnnectionViewPager
        val tabLayout: TabLayout = binding.profileConnectionTab

        viewPager.adapter = MyPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Mengikuti"
                1 -> tab.text = "Pengikut"
            }
        }.attach()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ProfileViewModel::class.java]
    }

    private fun setupObserver(){
            lifecycleScope.launchWhenResumed {
                AccessManager(requireContext())
                    .access
                    .collect { token ->
                        AccessManager(requireContext())
                            .accessUserId
                            .collect { userId ->
                                viewModel.requestProfile(token, userId)
                                    .observe(viewLifecycleOwner, Observer {
                                        it?.let { resource ->
                                            when (resource.status) {
                                                Status.SUCCESS -> {
                                                    resource.data?.let { profile ->
                                                        binding.apply {
                                                            topBarProfileConnection.title =
                                                                "@ ${profile.body()?.data?.username}"
                                                        }
                                                    }
                                                }
                                                Status.ERROR -> {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        it.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                                Status.LOADING -> {}
                                            }
                                        }
                                    })
                            }
                    }
            }

    }
}