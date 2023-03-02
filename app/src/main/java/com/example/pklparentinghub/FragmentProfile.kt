package com.example.pklparentinghub

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.databinding.FragmentArtichleBinding
import com.example.pklparentinghub.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view()
        setupPager()
        btnEditProfile()
    }

    private fun btnEditProfile(){
        view?.findViewById<Button>(R.id.btnProfile)?.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentProfile_to_fragmentEditProfile)
        }
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FragmentArtichle()
                1 -> FragmentLikes()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

    private fun setupPager(){
        val viewPager: ViewPager2 = binding.vpProfile
        val tabLayout: TabLayout = binding.tabLayout

        viewPager.adapter = MyPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Artikel Saya"
                1 -> tab.text = "Disukai"
            }
        }.attach()
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.tvFollowing.text.toString())
        val spannable2 = SpannableStringBuilder(binding.tvFollowers.text.toString())
        val greyColor = ForegroundColorSpan(Color.parseColor("#555555"))

        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(greyColor, 4, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(greyColor, 4, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFollowing.text = spannable
        binding.tvFollowers.text = spannable2
    }
}