package com.example.pklparentinghub

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pklparentinghub.databinding.FragmentMainProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainProfileFragment : Fragment(R.layout.fragment_main_profile) {

    private var _binding: FragmentMainProfileBinding? = null
    private val binding get() = _binding!!

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
        view()
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
                0 -> ProfileArticleFragment()
                1 -> ProfileLikesFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }

    private fun editProfile(){
        binding.mainButtonEditProfile.setOnClickListener {
            val fragment = ProfileEditFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.mainProfileFollowing.text.toString())
        val spannable2 = SpannableStringBuilder(binding.mainProfileFollowers.text.toString())
        val greyColor = ForegroundColorSpan(Color.parseColor("#A9A9A9"))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = ProfileConnectionFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
            }
        }

        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(greyColor, 4, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(greyColor, 4, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 4, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(clickableSpan, 4, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mainProfileFollowing.text = spannable
        binding.mainProfileFollowers.text = spannable2
        binding.mainProfileFollowers.movementMethod = LinkMovementMethod.getInstance()
        binding.mainProfileFollowing.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}