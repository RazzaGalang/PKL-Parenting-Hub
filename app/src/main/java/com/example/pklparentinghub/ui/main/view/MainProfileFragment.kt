package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.databinding.FragmentMainProfileBinding
import com.example.pklparentinghub.shimmer.ShimmerArticleProfileRecyclerFragment
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect

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
        val view =  binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        editProfile()
        textFollower()
        textFollowing()
        navBar()
        floatingBar()
        setupViewModel()
        setupObserver()
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

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ProfileViewModel::class.java]
    }

    private fun navBar(){
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.VISIBLE
    }

    private fun floatingBar(){
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.mainButtonCreateArticle)
        fab.visibility = View.VISIBLE
    }

    private fun editProfile(){
            binding.mainProfileButtonEditProfile.setOnClickListener {
                val fragment = ProfileEditFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
            }
    }

    private fun textFollower(){
        binding.mainProfileFollower.setOnClickListener {
            val fragment = ProfileConnectionFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }

    private fun textFollowing(){
        binding.mainProfileFollowing.setOnClickListener {
            val fragment = ProfileConnectionFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }
    
    private fun showLoading(loading: Boolean){
        binding.apply {
            group.isVisible = !loading
            profileShimmer.shimmerFrameLayout.isVisible = loading
        }
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfileArticleFragment()
                1 -> ProfileLikesFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

//    override fun onItemCLick(item: Article) {
//        val intent = Intent(this.context, DetailArticleActivity::class.java)
//        intent.putExtra("id", item.id)
//        startActivity(intent)
//    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.requestProfile(token, 11).observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            showLoading( resource.status == Status.LOADING)
                            when(resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { profile ->
                                        binding.apply {
                                            val mainCover = "${profile.body()?.data?.profileCover}"
                                            val banner = mainProfileBanner
                                            Glide.with(banner)
                                                .load(mainCover)
                                                .into(banner)
                                            val mainPicture = "${profile.body()?.data?.profilePicture}"
                                            val picture = mainProfilePicture
                                            Glide.with(picture)
                                                .load(mainPicture)
                                                .into(picture)
                                            mainProfileFullName.text = "${profile.body()?.data?.fullName}"
                                            mainProfileUsername.text = "${profile.body()?.data?.username}"
                                            mainProfileDescription.text = "${profile.body()?.data?.description}"
                                            mainProfileFollowing.text = "${profile.body()?.data?.following} Mengikuti"
                                            mainProfileFollower.text = "${profile.body()?.data?.followers} Pengikut"
                                        }
                                    }
                                }
                                Status.ERROR -> {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                                }
                                Status.LOADING -> {}
                            }
                        }
                    })
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}