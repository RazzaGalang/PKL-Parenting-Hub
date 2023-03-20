package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentProfileArticleBinding
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter
import com.example.pklparentinghub.utils.Status

class ProfileArticleFragment : Fragment() {

    private var _binding: FragmentProfileArticleBinding? = null
    private val binding get() = _binding!!

    private val shimmerAdapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }


    private fun setupUI() {
        binding.shimmerRecycler.adapter = shimmerAdapter
    }


}



