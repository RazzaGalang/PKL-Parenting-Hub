package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.utils.AccessManager

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                AccessManager(requireContext())
                    .access
                    .collect { token ->
                        val emptyString = ""
                        if (token == emptyString)
                            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthOnBoardingFragment())
                        else
                            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainActivity())
                    }
            }
        }, 3000)
    }

}