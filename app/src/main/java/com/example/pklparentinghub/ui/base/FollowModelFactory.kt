package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.ui.main.viewmodel.FollowViewModel

class FollowModelFactory (private val apiHelper: ApiHelper): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(ProfileRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}