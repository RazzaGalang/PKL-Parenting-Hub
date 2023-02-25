package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pklparentinghub.databinding.FragmentShimmerProfileMainBinding

class ShimmerProfileMainFragment : Fragment() {

    private var _binding: FragmentShimmerProfileMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShimmerProfileMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}