package com.example.pklparentinghub.shimmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.FragmentShimmerArticleProfileRecyclerBinding
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleProfileAdapter

class ShimmerArticleProfileRecyclerFragment : Fragment() {

    private var _binding: FragmentShimmerArticleProfileRecyclerBinding? = null
    private val binding get() = _binding!!
    private val adapter : ShimmerArticleProfileAdapter = ShimmerArticleProfileAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShimmerArticleProfileRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shimmerRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}