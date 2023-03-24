package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

//    private fun setupViewModel (){
//        viewModel = ViewModelProvider(
//            this,
//            SplashScreenViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
//        )[SplashScreenVIewModel::class.java]
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
//            lifecycleScope.launchWhenResumed {
//                viewModel.isFirstTimeAccess.collectLatest {
//                    viewModel.setHomeFirstTimeAccess(true)
//                    if (it) {
//                        navController.navigateOrNull(
//                            SplashScreenFragmentDirections.actionToOnboarding(),
//                            clearTask = true
//                        )
//                    } else {
//                        navController.navigateOrNull(
//                            SplashScreenFragmentDirections.actionToMain(),
//                            clearTask = true
//                        )
//                    }
//                }
//            }

            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthOnBoardingFragment())
        }, 1500)
    }

}