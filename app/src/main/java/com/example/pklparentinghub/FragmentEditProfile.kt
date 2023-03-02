package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pklparentinghub.databinding.FragmentEditProfileBinding
import com.example.pklparentinghub.databinding.FragmentProfileBinding

class FragmentEditProfile : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
}