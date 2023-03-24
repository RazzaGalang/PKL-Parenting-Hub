package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper

class SplashScreenViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
//            return SplashScreenViewModel(AuthRepository(apiHelper)) as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }
}