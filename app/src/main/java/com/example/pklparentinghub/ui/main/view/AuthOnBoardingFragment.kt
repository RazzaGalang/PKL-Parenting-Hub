package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentAuthOnBoardingBinding

class AuthOnBoardingFragment : Fragment() {

    private var _binding: FragmentAuthOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthOnBoardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation()
    }

    private fun initNavigation(){
        binding.onBoardingButtonContinue.setOnClickListener {
            findNavController().navigate(AuthOnBoardingFragmentDirections.actionAuthOnBoardingFragmentToAuthLoginFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}