package com.example.pklparentinghub.shimmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.FragmentShimmerArticleHomeRecyclerBinding
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter

class ShimmerArticleHomeRecyclerFragment : Fragment() {

    private var _binding: FragmentShimmerArticleHomeRecyclerBinding? = null
    private val binding get() = _binding!!
    private val adapter : ShimmerArticleHomeAdapter = ShimmerArticleHomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShimmerArticleHomeRecyclerBinding.inflate(inflater, container, false)
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